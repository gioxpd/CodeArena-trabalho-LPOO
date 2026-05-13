package game;

import battle.BattleManager;
import characters.Character;
import characters.Warrior;
import characters.Healer;
import characters.Mage;
import characters.Archer;
import characters.Tank;
import enemies.Enemy;
import enemies.EnemyFactory;
import questions.QuestionBank;
import system.Player;
import system.ScoreSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Game {
    private Player player;
    private BattleManager battleManager;
    private QuestionBank questionBank;
    private ScoreSystem scoreSystem;
    private Scanner scanner;
    private boolean running;
    private int currentStage;
    private static final int MAX_STAGES = 5;

    public Game() {
        this.scanner = new Scanner(System.in);
        this.questionBank = new QuestionBank();
        this.scoreSystem = new ScoreSystem();
        this.running = false;
        this.currentStage = 1;
    }

    public void start() {
        displayTitle();
        running = true;

        while (running) {
            displayMainMenu();
            int choice = readIntInput(1, 4);
            handleMenuChoice(choice);
        }

        scanner.close();
        System.out.println("\n⚔️ Obrigado por jogar CodeArena! Até a próxima aventura! ⚔️");
    }

    private void displayTitle() {
        System.out.println("\n");
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                               ║");
        System.out.println("║    ██╗     ██████╗  ██████╗  ██████╗                          ║");
        System.out.println("║    ██║     ██╔══██╗██╔═══██╗██╔═══██╗                         ║");
        System.out.println("║    ██║     ██████╔╝██║   ██║██║   ██║                         ║");
        System.out.println("║    ██║     ██╔═══╝ ██║   ██║██║   ██║                         ║");
        System.out.println("║    ███████╗██║     ╚██████╔╝╚██████╔╝                         ║");
        System.out.println("║    ╚══════╝╚═╝      ╚═════╝  ╚═════╝                          ║");
        System.out.println("║                                                               ║");
        System.out.println("║              ⚔️  BATALHA DO CONHECIMENTO  ⚔️                  ║");
        System.out.println("║                                                               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println("\n       Um jogo de quiz educacional com batalhas em turnos\n");
    }

    private void displayMainMenu() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║           MENU PRINCIPAL              ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║  1. 🎮 Nova Aventura                  ║");
        System.out.println("║  2. 📖 Como Jogar                     ║");
        System.out.println("║  3. 👥 Conhecer os Heróis             ║");
        System.out.println("║  4. 🚪 Sair                           ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.print("Escolha uma opção: ");
    }

    private void handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                startNewGame();
                break;
            case 2:
                displayHowToPlay();
                break;
            case 3:
                displayHeroes();
                break;
            case 4:
                running = false;
                break;
        }
    }

    private void startNewGame() {
        System.out.print("\n🧙 Aventureiro, qual é o seu nome? ");
        String playerName = scanner.nextLine().trim();
        if (playerName.isEmpty()) {
            playerName = "Herói Anônimo";
        }

        // Cria o grupo de heróis
        List<Character> heroes = createHeroParty();
        player = new Player(playerName, heroes);
        scoreSystem.reset();
        currentStage = 1;

        System.out.println("\n⚔️ Bem-vindo, " + playerName + "!");
        System.out.println("Você lidera um grupo de 5 heróis corajosos.");
        System.out.println("Enfrente os inimigos respondendo perguntas corretamente!");
        pressEnterToContinue();

        // Loop principal das batalhas
        while (currentStage <= MAX_STAGES && hasHeroesAlive()) {
            Enemy enemy = EnemyFactory.createEnemyForStage(currentStage);
            battleManager = new BattleManager(player, enemy, questionBank, scoreSystem);

            System.out.println("\n════════════════════════════════════════");
            System.out.println("           ESTÁGIO " + currentStage + " de " + MAX_STAGES);
            System.out.println("════════════════════════════════════════");

            boolean victory = battleManager.startBattle();

            if (victory) {
                currentStage++;
                if (currentStage <= MAX_STAGES) {
                    System.out.println("\n🎉 Vitória! Preparando próximo desafio...");
                    healPartyPartially();
                    pressEnterToContinue();
                }
            } else {
                System.out.println("\n💀 Seu grupo foi derrotado...");
                break;
            }
        }

        // Fim do jogo
        displayGameOver();
    }


    private List<Character> createHeroParty() {
        List<Character> heroes = new ArrayList<>();
        heroes.add(new Warrior("Astyanax"));
        heroes.add(new Healer("Fallistra"));
        heroes.add(new Mage("Fynrall"));
        heroes.add(new Archer("MudMug"));
        heroes.add(new Tank("Edrius"));
        return heroes;
    }

    private boolean hasHeroesAlive() {
        return player.getAliveHeroes().size() > 0;
    }

    private void healPartyPartially() {
        for (Character hero : player.getHeroes()) {
            if (hero.isAlive()) {
                int healAmount = hero.getMaxHp() / 4;
                hero.heal(healAmount);
            }
        }
        System.out.println("💚 Seu grupo recuperou parte da vida!");
    }

    private void displayGameOver() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║            FIM DA AVENTURA            ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║  Jogador: " + padRight(player.getName(), 26) + " ║");
        System.out.println("║  Estágios completados: " + padRight(String.valueOf(currentStage - 1) + "/" + MAX_STAGES, 13) + " ║");
        System.out.println("║  Pontuação final: " + padRight(String.valueOf(scoreSystem.getTotalScore()), 18) + " ║");
        System.out.println("║  Perguntas certas: " + padRight(String.valueOf(scoreSystem.getCorrectAnswers()), 17) + " ║");
        System.out.println("║  Perguntas erradas: " + padRight(String.valueOf(scoreSystem.getWrongAnswers()), 16) + " ║");
        System.out.println("╚═══════════════════════════════════════╝");

        if (currentStage > MAX_STAGES) {
            System.out.println("\n🏆 PARABÉNS! Você completou todas as batalhas!");
            System.out.println("O conhecimento é a maior arma de todas!");
        }

        pressEnterToContinue();
    }

    private void displayHowToPlay() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                      COMO JOGAR                               ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════╣");
        System.out.println("║                                                               ║");
        System.out.println("║  🎯 OBJETIVO                                                  ║");
        System.out.println("║  Derrote os inimigos respondendo perguntas corretamente!      ║");
        System.out.println("║                                                               ║");
        System.out.println("║  ⚔️ BATALHA                                                   ║");
        System.out.println("║  • Cada rodada, uma pergunta aparece na tela                  ║");
        System.out.println("║  • Responda corretamente para causar dano ao inimigo          ║");
        System.out.println("║  • Respostas erradas fazem seu grupo receber dano             ║");
        System.out.println("║                                                               ║");
        System.out.println("║  ✅ ACERTOU                                                   ║");
        System.out.println("║  • Causa dano ao inimigo                                      ║");
        System.out.println("║  • Pode ativar habilidades especiais                          ║");
        System.out.println("║  • Ganha pontos de experiência                                ║");
        System.out.println("║                                                               ║");
        System.out.println("║  ❌ ERROU                                                     ║");
        System.out.println("║  • Seu grupo recebe dano                                      ║");
        System.out.println("║  • Perde vantagens acumuladas                                 ║");
        System.out.println("║                                                               ║");
        System.out.println("║  📚 CATEGORIAS                                                ║");
        System.out.println("║  História | Biologia | Programação | Curiosidades de Games    ║");
        System.out.println("║                                                               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        pressEnterToContinue();
    }

    private void displayHeroes() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    CONHEÇA OS HERÓIS                          ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");

        System.out.println("\n⚔️ ASTYANAX - Guerreiro");
        System.out.println("   Função: DPS físico equilibrado");
        System.out.println("   • Vida alta, ataque forte, defesa média");
        System.out.println("   • Habilidade: Golpe Crítico - Dobra o dano do próximo ataque");

        System.out.println("\n💚 FALLISTRA - Curandeira");
        System.out.println("   Função: Suporte e cura");
        System.out.println("   • Baixa defesa, habilidades de cura");
        System.out.println("   • Habilidade: Cura Divina - Recupera HP de todo o grupo");

        System.out.println("\n🔮 FYNRALL - Mago");
        System.out.println("   Função: Dano mágico");
        System.out.println("   • Alto ataque, pouca vida");
        System.out.println("   • Habilidade: Explosão Arcana - Causa dano massivo");

        System.out.println("\n🏹 MUDMUG - Arqueiro Goblin");
        System.out.println("   Função: Velocidade e precisão");
        System.out.println("   • Ataques rápidos, chance de esquiva");
        System.out.println("   • Habilidade: Flecha Certeira - Ataque com crítico garantido");

        System.out.println("\n🛡️ EDRIUS - Golem Tank");
        System.out.println("   Função: Defesa");
        System.out.println("   • Defesa extremamente alta, absorve dano");
        System.out.println("   • Habilidade: Escudo de Muralha - Protege aliados por 2 turnos");

        pressEnterToContinue();
    }

    private int readIntInput(int min, int max) {
        int input = -1;
        while (input < min || input > max) {
            try {
                String line = scanner.nextLine();
                input = Integer.parseInt(line.trim());
                if (input < min || input > max) {
                    System.out.print("Por favor, escolha entre " + min + " e " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Digite um número: ");
            }
        }
        return input;
    }

    private void pressEnterToContinue() {
        System.out.print("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }

    private String padRight(String text, int length) {
        if (text.length() >= length) {
            return text.substring(0, length);
        }
        StringBuilder sb = new StringBuilder(text);
        while (sb.length() < length) {
            sb.append(" ");
        }
        return sb.toString();
    }
}