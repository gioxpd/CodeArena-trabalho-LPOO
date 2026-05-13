package battle;

import characters.Character;
import characters.Healer;
import characters.Mage;
import characters.Tank;
import enemies.Enemy;
import questions.AnswerEvaluator;
import questions.AnswerEvaluator.EvaluationResult;
import questions.Question;
import questions.QuestionBank;
import system.Player;
import system.ScoreSystem;

import java.util.List;
import java.util.Scanner;


public class BattleManager {
    private Player player;
    private Enemy enemy;
    private QuestionBank questionBank;
    private ScoreSystem scoreSystem;
    private AnswerEvaluator evaluator;
    private Scanner scanner;
    private int roundNumber;
    private boolean battleOver;
    private static final int ABILITY_CHARGE_THRESHOLD = 2;
    private int abilityCharges;
    private Character nextAttacker; // Personagem que usou habilidade ataca em seguida

    /**
     * Construtor do gerenciador de batalha.
     *
     * @param player Jogador
     * @param enemy Inimigo
     * @param questionBank Banco de perguntas
     * @param scoreSystem Sistema de pontuação
     */
    public BattleManager(Player player, Enemy enemy, QuestionBank questionBank, ScoreSystem scoreSystem) {
        this.player = player;
        this.enemy = enemy;
        this.questionBank = questionBank;
        this.scoreSystem = scoreSystem;
        this.evaluator = new AnswerEvaluator();
        this.scanner = new Scanner(System.in);
        this.roundNumber = 0;
        this.battleOver = false;
        this.abilityCharges = 0;
        this.nextAttacker = null;

        // Configura a curandeira com a lista do grupo
        for (Character hero : player.getHeroes()) {
            if (hero instanceof Healer) {
                ((Healer) hero).setParty(player.getHeroes());
            }
            if (hero instanceof Tank) {
                ((Tank) hero).setProtectedAllies(player.getHeroes());
            }
        }
    }

    /**
     * Inicia a batalha e retorna true se o jogador vencer.
     *
     * @return true se vitória, false se derrota
     */
    public boolean startBattle() {
        displayBattleStart();

        while (!battleOver) {
            roundNumber++;
            Round round = new Round(roundNumber);
            executeRound(round);

            // Verifica condições de fim de batalha
            if (!enemy.isAlive()) {
                battleOver = true;
                displayVictory();
                return true;
            }

            if (player.getAliveHeroes().isEmpty()) {
                battleOver = true;
                displayDefeat();
                return false;
            }
        }

        return false;
    }

    private void displayBattleStart() {
        System.out.println("\n════════════════════════════════════════");
        System.out.println("         ⚔️ BATALHA INICIADA! ⚔️");
        System.out.println("════════════════════════════════════════");
        System.out.println(enemy.display());
        System.out.println("Seu grupo enfrenta: " + enemy.getIcon() + " " + enemy.getName() + "!");
        pressEnterToContinue();
    }

    /**
     * Executa uma rodada de batalha.
     *
     * @param round Rodada atual
     */
    private void executeRound(Round round) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║            RODADA " + padRight(String.valueOf(roundNumber), 3) + "                 ║");
        System.out.println("╚═══════════════════════════════════════╝");

        // Exibe status do inimigo
        System.out.println("\n🎯 INIMIGO:");
        System.out.println(enemy.getStatus());

        // Exibe status do grupo
        displayPartyStatus();

        // Verifica se pode usar habilidade
        if (abilityCharges >= ABILITY_CHARGE_THRESHOLD) {
            offerAbilityUse();
        }

        // Obtém e exibe pergunta
        Question question = questionBank.getRandomQuestion();
        round.setQuestion(question);

        System.out.println("\n" + question.display());
        System.out.print("\nSua resposta: ");
        String answer = scanner.nextLine();

        // Avalia resposta
        EvaluationResult result = evaluator.evaluate(question, answer);
        round.setResult(result);

        processResult(result, question);

        // Atualiza tanks se houver
        for (Character hero : player.getHeroes()) {
            if (hero instanceof Tank) {
                ((Tank) hero).updateStoneWall();
            }
        }
    }

    /**
     * Processa o resultado da resposta.
     *
     * @param result Resultado da avaliação
     * @param question Pergunta respondida
     */
    private void processResult(EvaluationResult result, Question question) {
        System.out.println();

        if (result.isCorrect()) {
            processCorrectAnswer(result, question);
        } else {
            processWrongAnswer(result, question);
        }

        pressEnterToContinue();
    }

    /**
     * Processa uma resposta correta.
     */
    private void processCorrectAnswer(EvaluationResult result, Question question) {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║         ✅ RESPOSTA CORRETA!          ║");
        System.out.println("╚═══════════════════════════════════════╝");

        // Seleciona atacante
        Character attacker = selectAttacker();
        int damage = attacker.atacar() + result.getDamageToEnemy();

        // Bônus para mago em perguntas difíceis
        if (attacker instanceof Mage && question.getDifficulty().getLevel() >= 2) {
            ((Mage) attacker).onCorrectAnswer(question.getDifficulty().getLevel());
            damage = (int)(damage * 1.2);
            System.out.println("🔮 " + attacker.getName() + " recebe bônus de dano mágico!");
        }

        int actualDamage = enemy.receberDano(damage);

        System.out.println("\n⚔️ " + attacker.getName() + " ataca!");
        System.out.println("💥 " + enemy.getName() + " recebeu " + actualDamage + " de dano!");

        if (result.getStreak() > 1) {
            System.out.println("🔥 Sequência de " + result.getStreak() + " acertos!");
        }

        // Adiciona pontos e cargas de habilidade
        scoreSystem.addCorrectAnswer(result.getPointsEarned());
        abilityCharges++;

        System.out.println("⭐ +" + result.getPointsEarned() + " pontos");
    }

    /**
     * Processa uma resposta errada.
     */
    private void processWrongAnswer(EvaluationResult result, Question question) {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║         ❌ RESPOSTA INCORRETA!        ║");
        System.out.println("╚═══════════════════════════════════════╝");

        System.out.println("\n📖 A resposta correta era: " + question.getCorrectAnswer());

        // Inimigo ataca
        int enemyDamage = enemy.atacar() + result.getDamageToPlayer();

        // Verifica se tank está protegendo
        Tank protector = null;
        for (Character hero : player.getHeroes()) {
            if (hero instanceof Tank && hero.isAlive() && ((Tank) hero).isStoneWallActive()) {
                protector = (Tank) hero;
                break;
            }
        }

        System.out.println("\n😈 " + enemy.getName() + " contra-ataca!");

        if (protector != null) {
            int reducedDamage = protector.protectAlly(null, enemyDamage);
            System.out.println("🛡️ " + protector.getName() + " absorve parte do dano!");
            distributeEnemyDamage(reducedDamage);
        } else {
            distributeEnemyDamage(enemyDamage);
        }

        // Registra erro
        scoreSystem.addWrongAnswer();
        abilityCharges = Math.max(0, abilityCharges - 1);
    }

    /**
     * Distribui dano do inimigo entre os heróis vivos.
     */
    private void distributeEnemyDamage(int totalDamage) {
        List<Character> aliveHeroes = player.getAliveHeroes();
        if (aliveHeroes.isEmpty()) return;

        // Distribui dano aleatoriamente entre 1-3 heróis
        int targets = Math.min(aliveHeroes.size(), 1 + (int)(Math.random() * 2));
        int damagePerTarget = totalDamage / targets;

        for (int i = 0; i < targets && i < aliveHeroes.size(); i++) {
            Character target = aliveHeroes.get(i);
            int actualDamage = target.receberDano(damagePerTarget);
            System.out.println("💔 " + target.getName() + " recebeu " + actualDamage + " de dano!");

            if (!target.isAlive()) {
                System.out.println("☠️ " + target.getName() + " foi derrotado!");
            }
        }
    }

    /**
     * Seleciona o atacante do grupo.
     * Se um personagem usou habilidade recentemente, ele ataca.
     * Caso contrário, seleciona aleatoriamente.
     */
    private Character selectAttacker() {
        List<Character> aliveHeroes = player.getAliveHeroes();
        if (aliveHeroes.isEmpty()) return null;

        // Se há um atacante definido (usou habilidade), ele ataca
        if (nextAttacker != null && nextAttacker.isAlive()) {
            Character attacker = nextAttacker;
            nextAttacker = null; // Limpa para próxima rodada
            return attacker;
        }

        // Caso contrário, seleciona aleatoriamente
        return aliveHeroes.get((int)(Math.random() * aliveHeroes.size()));
    }

    /**
     * Oferece ao jogador usar uma habilidade especial.
     */
    private void offerAbilityUse() {
        System.out.println("\n⚡ HABILIDADE ESPECIAL DISPONÍVEL! ⚡");
        System.out.println("Você acumulou " + abilityCharges + " cargas.");
        System.out.println("Deseja usar uma habilidade? (S/N)");
        System.out.print("> ");

        String response = scanner.nextLine().trim().toUpperCase();

        if (response.equals("S") || response.equals("SIM")) {
            selectAndUseAbility();
        }
    }

    /**
     * Permite ao jogador selecionar e usar uma habilidade.
     */
    private void selectAndUseAbility() {
        List<Character> aliveHeroes = player.getAliveHeroes();

        System.out.println("\nEscolha um herói para usar a habilidade:");
        for (int i = 0; i < aliveHeroes.size(); i++) {
            Character hero = aliveHeroes.get(i);
            System.out.println((i + 1) + ". " + hero.getClassIcon() + " " + hero.getName() +
                    " (" + hero.getCharacterClass() + ")");
        }
        System.out.print("> ");

        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice >= 1 && choice <= aliveHeroes.size()) {
                Character hero = aliveHeroes.get(choice - 1);
                String result = hero.usarHabilidade();
                System.out.println("\n" + result);
                abilityCharges = 0;

                // Define o herói que usou a habilidade como o próximo atacante
                nextAttacker = hero;
                System.out.println("⚡ " + hero.getName() + " está preparado para atacar!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Escolha inválida. Habilidade não usada.");
        }
    }

    private void displayPartyStatus() {
        System.out.println("\n👥 SEU GRUPO:");
        for (Character hero : player.getHeroes()) {
            String status = hero.isAlive() ? hero.getStatus() : "☠️ " + hero.getName() + " (Derrotado)";
            System.out.println("   " + status);
        }
    }

    private void displayVictory() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║          🏆 VITÓRIA! 🏆               ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println("\n" + enemy.getName() + " foi derrotado!");
        System.out.println("Rodadas: " + roundNumber);
        System.out.println("Pontuação nesta batalha: " + scoreSystem.getCurrentBattleScore());

        int bonusPoints = roundNumber < 5 ? 200 : (roundNumber < 10 ? 100 : 50);
        scoreSystem.addBonusPoints(bonusPoints);
        System.out.println("Bônus de eficiência: +" + bonusPoints + " pontos!");
    }

    private void displayDefeat() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║          💀 DERROTA... 💀             ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println("\nSeu grupo foi derrotado por " + enemy.getName() + "...");
        System.out.println("Rodadas sobrevividas: " + roundNumber);
    }

    private void pressEnterToContinue() {
        System.out.print("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }

    private String padRight(String text, int length) {
        if (text.length() >= length) return text;
        StringBuilder sb = new StringBuilder(text);
        while (sb.length() < length) sb.append(" ");
        return sb.toString();
    }
}