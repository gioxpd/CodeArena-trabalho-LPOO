package questions;


public abstract class Question {
    protected String statement;
    protected Category category;
    protected Difficulty difficulty;
    protected String correctAnswer;
    protected String hint;


    public enum Category {
        HISTORIA("História", "📜"),
        BIOLOGIA("Biologia", "🧬"),
        PROGRAMACAO("Programação", "💻"),
        GAMES("Curiosidades de Games", "🎮");

        private final String displayName;
        private final String icon;

        Category(String displayName, String icon) {
            this.displayName = displayName;
            this.icon = icon;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getIcon() {
            return icon;
        }

        @Override
        public String toString() {
            return icon + " " + displayName;
        }
    }

    public enum Difficulty {
        FACIL(1, "Fácil", "🟢"),
        MEDIO(2, "Médio", "🟡"),
        DIFICIL(3, "Difícil", "🔴");

        private final int level;
        private final String displayName;
        private final String icon;

        Difficulty(int level, String displayName, String icon) {
            this.level = level;
            this.displayName = displayName;
            this.icon = icon;
        }

        public int getLevel() {
            return level;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getIcon() {
            return icon;
        }

        @Override
        public String toString() {
            return icon + " " + displayName;
        }
    }

    public Question(String statement, Category category, Difficulty difficulty, String correctAnswer) {
        this.statement = statement;
        this.category = category;
        this.difficulty = difficulty;
        this.correctAnswer = correctAnswer;
        this.hint = "";
    }

    public abstract boolean verificarResposta(String answer);

    public abstract String display();

    public int getPoints() {
        return difficulty.getLevel() * 100;
    }

    public int getDamageBonus() {
        return difficulty.getLevel() * 10;
    }

    // Getters e Setters

    public String getStatement() {
        return statement;
    }

    public Category getCategory() {
        return category;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    protected String getHeader() {
        return "╔═══════════════════════════════════════════════════════════════╗\n" +
                "║  " + category + " | " + difficulty + padSpaces(40 - category.getDisplayName().length() - difficulty.getDisplayName().length()) + "║\n" +
                "╠═══════════════════════════════════════════════════════════════╣";
    }

    protected String padSpaces(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }
}