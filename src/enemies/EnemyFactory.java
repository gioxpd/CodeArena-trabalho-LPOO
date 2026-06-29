package enemies;

import questions.Question.Category;
import java.util.Random;


public class EnemyFactory {
    private static final Random random = new Random();


    public static Enemy createEnemyForStage(int stage) {
        Enemy enemy;

        switch (stage) {
            case 1:
                enemy = createBasicEnemy();
                break;
            case 2:
                enemy = createIntermediateEnemy();
                break;
            case 3:
                enemy = createAdvancedEnemy();
                break;
            case 4:
                enemy = createEliteEnemy();
                break;
            case 5:
                enemy = createBoss();
                break;
            default:
                enemy = createBasicEnemy();
        }

        enemy.setStage(stage);
        return enemy;
    }

    // Métodos privados para criar tipos específicos de inimigos

    private static Enemy createBasicEnemy() {
        String[] names = {"Slime Verde", "Goblin Iniciante", "Rato Gigante", "Esqueleto Fraco"};
        String[] icons = {"🟢", "👺", "🐀", "💀"};

        int index = random.nextInt(names.length);
        Enemy enemy = new Enemy(names[index], 80, 12, 5, null, icons[index]);
        enemy.setDescription("Um inimigo básico.");
        return enemy;
    }

    private static Enemy createIntermediateEnemy() {
        String[] names = {"Orc Guerreiro", "Lobo Sombrio", "Aranha Venenosa", "Zumbi"};
        String[] icons = {"👹", "🐺", "🕷️", "🧟"};

        int index = random.nextInt(names.length);
        Enemy enemy = new Enemy(names[index], 120, 18, 10, null, icons[index]);
        enemy.setDescription("Um inimigo de nível médio.");
        return enemy;
    }

    private static Enemy createAdvancedEnemy() {
        String[] names = {"Cavaleiro Negro", "Mago das Trevas", "Golem de Pedra", "Vampiro"};
        String[] icons = {"🗡️", "🧙", "🪨", "🧛"};

        int index = random.nextInt(names.length);
        Enemy enemy = new Enemy(names[index], 160, 25, 15, null, icons[index]);
        enemy.setDescription("Um inimigo poderoso.");
        return enemy;
    }

    private static Enemy createEliteEnemy() {
        String[] names = {"Dragão Menor", "Demônio", "Lich", "Quimera"};
        String[] icons = {"🐲", "👿", "☠️", "🦁"};

        int index = random.nextInt(names.length);
        Enemy enemy = new Enemy(names[index], 300, 32, 20, null, icons[index]);
        enemy.setDescription("Um inimigo de elite.");
        return enemy;
    }

    private static Enemy createBoss() {
        String[] names = {"Rei Demônio Malachar", "Dragão Ancestral Vortex",
                "O Devorador de Almas", "Imperador das Sombras"};
        String[] icons = {"👑", "🐉", "💀", "🌑"};

        int index = random.nextInt(names.length);
        Enemy enemy = new Enemy(names[index], 550, 40, 25, null, icons[index]);
        enemy.setDescription("CHEFE FINAL!");
        return enemy;
    }
}