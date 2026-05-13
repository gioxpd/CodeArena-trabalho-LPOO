package system;

/**
 * Sistema de pontuação do jogo.
 * Controla pontos, bônus e estatísticas.
 *
 * Demonstra: Encapsulamento e responsabilidade única
 */
public class ScoreSystem {
    private int totalScore;
    private int currentBattleScore;
    private int correctAnswers;
    private int wrongAnswers;
    private int currentStreak;
    private int maxStreak;
    private int bonusPoints;

    /**
     * Construtor do sistema de pontuação.
     */
    public ScoreSystem() {
        reset();
    }

    /**
     * Reseta todas as estatísticas.
     */
    public void reset() {
        this.totalScore = 0;
        this.currentBattleScore = 0;
        this.correctAnswers = 0;
        this.wrongAnswers = 0;
        this.currentStreak = 0;
        this.maxStreak = 0;
        this.bonusPoints = 0;
    }

    /**
     * Reseta apenas a pontuação da batalha atual.
     */
    public void resetBattleScore() {
        this.currentBattleScore = 0;
    }

    /**
     * Registra uma resposta correta.
     *
     * @param points Pontos ganhos
     */
    public void addCorrectAnswer(int points) {
        correctAnswers++;
        currentStreak++;

        if (currentStreak > maxStreak) {
            maxStreak = currentStreak;
        }

        // Aplica bônus de sequência
        int streakBonus = calculateStreakBonus();
        int totalPoints = points + streakBonus;

        currentBattleScore += totalPoints;
        totalScore += totalPoints;
    }

    /**
     * Registra uma resposta errada.
     */
    public void addWrongAnswer() {
        wrongAnswers++;
        currentStreak = 0;
    }

    /**
     * Adiciona pontos de bônus.
     *
     * @param points Pontos bônus
     */
    public void addBonusPoints(int points) {
        bonusPoints += points;
        totalScore += points;
        currentBattleScore += points;
    }

    /**
     * Calcula bônus baseado na sequência atual.
     *
     * @return Pontos de bônus
     */
    private int calculateStreakBonus() {
        if (currentStreak <= 1) return 0;
        if (currentStreak <= 3) return 10 * currentStreak;
        if (currentStreak <= 5) return 20 * currentStreak;
        return 30 * currentStreak;
    }

    /**
     * Calcula a taxa de acertos.
     *
     * @return Porcentagem de acertos
     */
    public double getAccuracyRate() {
        int total = correctAnswers + wrongAnswers;
        if (total == 0) return 0;
        return (double) correctAnswers / total * 100;
    }

    /**
     * Retorna o resumo das estatísticas.
     *
     * @return String formatada
     */
    public String getStatsSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔═══════════════════════════════════════╗\n");
        sb.append("║           📊 ESTATÍSTICAS             ║\n");
        sb.append("╠═══════════════════════════════════════╣\n");
        sb.append("║  Pontuação Total: ").append(padRight(String.valueOf(totalScore), 19)).append("║\n");
        sb.append("║  Respostas Certas: ").append(padRight(String.valueOf(correctAnswers), 18)).append("║\n");
        sb.append("║  Respostas Erradas: ").append(padRight(String.valueOf(wrongAnswers), 17)).append("║\n");
        sb.append("║  Taxa de Acertos: ").append(padRight(String.format("%.1f%%", getAccuracyRate()), 19)).append("║\n");
        sb.append("║  Maior Sequência: ").append(padRight(String.valueOf(maxStreak), 19)).append("║\n");
        sb.append("║  Pontos Bônus: ").append(padRight(String.valueOf(bonusPoints), 22)).append("║\n");
        sb.append("╚═══════════════════════════════════════╝");
        return sb.toString();
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

    // Getters

    public int getTotalScore() {
        return totalScore;
    }

    public int getCurrentBattleScore() {
        return currentBattleScore;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public int getMaxStreak() {
        return maxStreak;
    }

    public int getBonusPoints() {
        return bonusPoints;
    }
}