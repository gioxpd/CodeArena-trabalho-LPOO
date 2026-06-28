package ui;

import characters.Character;
import characters.Healer;
import characters.Mage;
import characters.Tank;
import enemies.Enemy;
import enemies.EnemyFactory;
import questions.AnswerEvaluator;
import questions.AnswerEvaluator.EvaluationResult;
import questions.Question;
import questions.QuestionBank;
import system.Player;
import system.ScoreSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador de jogo orientado a eventos para a interface gráfica.
 *
 * Diferente do BattleManager (que era baseado em console com loops bloqueantes),
 * este controlador processa uma ação por vez e retorna o resultado, permitindo
 * que a GUI atualize a tela a cada interação.
 *
 * Reutiliza toda a lógica de modelo: Player, Enemy, Question, ScoreSystem.
 */
public class GameController {

    public static final int MAX_STAGES = 5;
    private static final int ABILITY_CHARGE_THRESHOLD = 2;

    private final QuestionBank questionBank;
    private final ScoreSystem scoreSystem;

    private Player player;
    private Enemy enemy;
    private AnswerEvaluator evaluator;
    private int currentStage;
    private int roundNumber;
    private int abilityCharges;
    private Character nextAttacker;
    private Question currentQuestion;

    public GameController() {
        this.questionBank = new QuestionBank();
        this.scoreSystem = new ScoreSystem();
    }

    /**
     * Inicia uma nova partida criando o grupo de heróis.
     */
    public void startNewGame(String playerName) {
        List<Character> heroes = new ArrayList<>();
        heroes.add(new characters.Warrior("Astyanax"));
        heroes.add(new characters.Healer("Fallistra"));
        heroes.add(new characters.Mage("Fynrall"));
        heroes.add(new characters.Archer("MudMug"));
        heroes.add(new characters.Tank("Edrius"));

        this.player = new Player(playerName, heroes);
        this.scoreSystem.reset();
        this.currentStage = 1;
        startStage();
    }

    /**
     * Prepara o inimigo e estado para o estágio atual.
     */
    private void startStage() {
        this.enemy = EnemyFactory.createEnemyForStage(currentStage);
        this.evaluator = new AnswerEvaluator();
        this.roundNumber = 0;
        this.abilityCharges = 0;
        this.nextAttacker = null;

        // Configura curandeira e tank com a lista do grupo
        for (Character hero : player.getHeroes()) {
            if (hero instanceof Healer) {
                ((Healer) hero).setParty(player.getHeroes());
            }
            if (hero instanceof Tank) {
                ((Tank) hero).setProtectedAllies(player.getHeroes());
            }
        }
        nextQuestion();
    }

    /**
     * Sorteia a próxima pergunta.
     */
    public Question nextQuestion() {
        this.roundNumber++;
        this.currentQuestion = questionBank.getRandomQuestion();
        return currentQuestion;
    }

    /**
     * Processa a resposta do jogador e retorna o resultado completo da rodada.
     */
    public TurnResult submitAnswer(String answer) {
        EvaluationResult eval = evaluator.evaluate(currentQuestion, answer);
        TurnResult turn = new TurnResult();
        turn.correct = eval.isCorrect();
        turn.correctAnswerText = currentQuestion.getCorrectAnswer();
        turn.streak = eval.getStreak();

        if (eval.isCorrect()) {
            processCorrect(eval, turn);
        } else {
            processWrong(eval, turn);
        }

        // Atualiza muralha de pedra dos tanks
        for (Character hero : player.getHeroes()) {
            if (hero instanceof Tank) {
                ((Tank) hero).updateStoneWall();
            }
        }

        turn.enemyDefeated = !enemy.isAlive();
        turn.partyDefeated = player.getAliveHeroes().isEmpty();
        return turn;
    }

    private void processCorrect(EvaluationResult eval, TurnResult turn) {
        Character attacker = selectAttacker();
        int damage = attacker.atacar() + eval.getDamageToEnemy();

        if (attacker instanceof Mage && currentQuestion.getDifficulty().getLevel() >= 2) {
            ((Mage) attacker).onCorrectAnswer(currentQuestion.getDifficulty().getLevel());
            damage = (int) (damage * 1.2);
            turn.mageBonus = true;
        }

        int actualDamage = enemy.receberDano(damage);
        turn.attackerName = attacker.getName();
        turn.damageToEnemy = actualDamage;
        turn.pointsEarned = eval.getPointsEarned();

        scoreSystem.addCorrectAnswer(eval.getPointsEarned());
        abilityCharges++;
        turn.abilityAvailable = abilityCharges >= ABILITY_CHARGE_THRESHOLD;
    }

    private void processWrong(EvaluationResult eval, TurnResult turn) {
        int enemyDamage = enemy.atacar() + eval.getDamageToPlayer();

        // Verifica proteção do tank
        Tank protector = null;
        for (Character hero : player.getHeroes()) {
            if (hero instanceof Tank && hero.isAlive() && ((Tank) hero).isStoneWallActive()) {
                protector = (Tank) hero;
                break;
            }
        }

        if (protector != null) {
            enemyDamage = protector.protectAlly(null, enemyDamage);
            turn.protectorName = protector.getName();
        }

        distributeEnemyDamage(enemyDamage, turn);

        scoreSystem.addWrongAnswer();
        abilityCharges = Math.max(0, abilityCharges - 1);
    }

    private void distributeEnemyDamage(int totalDamage, TurnResult turn) {
        List<Character> aliveHeroes = new ArrayList<>(player.getAliveHeroes());
        if (aliveHeroes.isEmpty()) return;

        // Embaralha os heróis vivos para que o inimigo ataque alvos aleatórios
        java.util.Collections.shuffle(aliveHeroes);

        int targets = Math.min(aliveHeroes.size(), 1 + (int) (Math.random() * 2));
        int damagePerTarget = totalDamage / targets;

        for (int i = 0; i < targets && i < aliveHeroes.size(); i++) {
            Character target = aliveHeroes.get(i);
            int actualDamage = target.receberDano(damagePerTarget);
            turn.hitTargets.add(target.getName() + " (-" + actualDamage + ")");
            if (!target.isAlive()) {
                turn.defeatedHeroes.add(target.getName());
            }
        }
    }

    /**
     * Seleciona o atacante: prioriza o herói que usou habilidade.
     */
    private Character selectAttacker() {
        List<Character> aliveHeroes = player.getAliveHeroes();
        if (aliveHeroes.isEmpty()) return null;

        if (nextAttacker != null && nextAttacker.isAlive()) {
            Character attacker = nextAttacker;
            nextAttacker = null;
            return attacker;
        }
        return aliveHeroes.get((int) (Math.random() * aliveHeroes.size()));
    }

    /**
     * Usa a habilidade especial de um herói e o define como próximo atacante.
     */
    public String useAbility(Character hero) {
        String result = hero.usarHabilidade();
        abilityCharges = 0;
        nextAttacker = hero;
        return result;
    }

    /**
     * Avança para o próximo estágio. Retorna true se há mais estágios.
     */
    public boolean advanceStage() {
        // Bônus de eficiência por vencer
        int bonusPoints = roundNumber < 5 ? 200 : (roundNumber < 10 ? 100 : 50);
        scoreSystem.addBonusPoints(bonusPoints);

        // Cura parcial do grupo
        for (Character hero : player.getHeroes()) {
            if (hero.isAlive()) {
                hero.heal(hero.getMaxHp() / 4);
            }
        }

        currentStage++;
        if (currentStage <= MAX_STAGES) {
            startStage();
            return true;
        }
        return false;
    }

    public boolean canUseAbility() {
        return abilityCharges >= ABILITY_CHARGE_THRESHOLD;
    }

    // Getters

    public Player getPlayer() { return player; }
    public Enemy getEnemy() { return enemy; }
    public ScoreSystem getScoreSystem() { return scoreSystem; }
    public int getCurrentStage() { return currentStage; }
    public int getRoundNumber() { return roundNumber; }
    public Question getCurrentQuestion() { return currentQuestion; }
    public int getAbilityCharges() { return abilityCharges; }

    /**
     * Estrutura que carrega o resultado de um turno para a GUI exibir.
     */
    public static class TurnResult {
        public boolean correct;
        public String correctAnswerText;
        public int streak;
        public String attackerName;
        public int damageToEnemy;
        public int pointsEarned;
        public boolean mageBonus;
        public String protectorName;
        public List<String> hitTargets = new ArrayList<>();
        public List<String> defeatedHeroes = new ArrayList<>();
        public boolean abilityAvailable;
        public boolean enemyDefeated;
        public boolean partyDefeated;
    }
}