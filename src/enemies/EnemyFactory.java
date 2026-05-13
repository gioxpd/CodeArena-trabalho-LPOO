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

    public static Enemy createEnemyByCategory(Category category, int stage) {
        int baseHp = 50 + (stage * 30);
        int baseAtk = 10 + (stage * 5);
        int baseDef = 5 + (stage * 3);

        switch (category) {
            case HISTORIA:
                return createHistoryEnemy(baseHp, baseAtk, baseDef);
            case BIOLOGIA:
                return createBiologyEnemy(baseHp, baseAtk, baseDef);
            case PROGRAMACAO:
                return createProgrammingEnemy(baseHp, baseAtk, baseDef);
            case GAMES:
                return createGamingEnemy(baseHp, baseAtk, baseDef);
            default:
                return createBasicEnemy();
        }
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
        Enemy enemy = new Enemy(names[index], 200, 32, 20, null, icons[index]);
        enemy.setDescription("Um inimigo de elite.");
        return enemy;
    }

    private static Enemy createBoss() {
        String[] names = {"Rei Demônio Malachar", "Dragão Ancestral Vortex",
                "O Devorador de Almas", "Imperador das Sombras"};
        String[] icons = {"👑", "🐉", "💀", "🌑"};

        int index = random.nextInt(names.length);
        Enemy enemy = new Enemy(names[index], 300, 40, 25, null, icons[index]);
        enemy.setDescription("CHEFE FINAL!");
        return enemy;
    }

    // Inimigos por categoria, não está sendo usado atualmente, mas vou tentar incrementar depois

    private static Enemy createHistoryEnemy(int hp, int atk, int def) {
        String[] names = {"Necromante da História", "Faraó Amaldiçoado",
                "Gladiador Fantasma", "Cavaleiro Templário"};
        String[] icons = {"📜", "🏛️", "⚔️", "🛡️"};

        int index = random.nextInt(names.length);
        Enemy enemy = new Enemy(names[index], hp, atk, def, Category.HISTORIA, icons[index]);
        enemy.setDescription("Guardião do conhecimento histórico.");
        return enemy;
    }

    private static Enemy createBiologyEnemy(int hp, int atk, int def) {
        String[] names = {"Slime Biológico", "Vírus Mutante",
                "Planta Carnívora", "Bactéria Gigante"};
        String[] icons = {"🧬", "🦠", "🌿", "🔬"};

        int index = random.nextInt(names.length);
        Enemy enemy = new Enemy(names[index], hp, atk, def, Category.BIOLOGIA, icons[index]);
        enemy.setDescription("Uma aberração da natureza.");
        return enemy;
    }

    private static Enemy createProgrammingEnemy(int hp, int atk, int def) {
        String[] names = {"Autômato Programador", "Bug Crítico",
                "Firewall Corrupto", "Vírus de Computador"};
        String[] icons = {"🤖", "🐛", "🔥", "💻"};

        int index = random.nextInt(names.length);
        Enemy enemy = new Enemy(names[index], hp, atk, def, Category.PROGRAMACAO, icons[index]);
        enemy.setDescription("Uma criatura do mundo digital.");
        return enemy;
    }

    private static Enemy createGamingEnemy(int hp, int atk, int def) {
        String[] names = {"Espírito dos Games", "Boss Pixelado",
                "Glitch Maligno", "NPC Corrompido"};
        String[] icons = {"🎮", "👾", "⚡", "🕹️"};

        int index = random.nextInt(names.length);
        Enemy enemy = new Enemy(names[index], hp, atk, def, Category.GAMES, icons[index]);
        enemy.setDescription("Uma entidade do mundo dos jogos.");
        return enemy;
    }
}