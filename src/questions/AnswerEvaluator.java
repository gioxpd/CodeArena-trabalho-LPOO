package questions;

//Classe responsável por avaliar respostas e calcular recompensas/penalidades.
public class AnswerEvaluator {
    private static final int BASE_DAMAGE = 20;
    private static final int BASE_PENALTY_DAMAGE = 15;
    private static final int STREAK_BONUS = 5;
    private static final int MAX_STREAK_BONUS = 25;

    private int currentStreak;
    private int maxStreak;

    public AnswerEvaluator() {
        this.currentStreak = 0;
        this.maxStreak = 0;
    }

    public EvaluationResult evaluate(Question question, String answer) {
        boolean isCorrect = question.verificarResposta(answer);

        if (isCorrect) {
            currentStreak++;
            if (currentStreak > maxStreak) {
                maxStreak = currentStreak;
            }

            int damage = calculateDamage(question);
            int points = calculatePoints(question);

            return new EvaluationResult(true, damage, 0, points, currentStreak);
        } else {
            int penaltyDamage = calculatePenaltyDamage(question);
            currentStreak = 0;

            return new EvaluationResult(false, 0, penaltyDamage, 0, 0);
        }
    }

    private int calculateDamage(Question question) {
        int difficultyBonus = question.getDamageBonus();
        int streakBonus = Math.min(currentStreak * STREAK_BONUS, MAX_STREAK_BONUS);
        return BASE_DAMAGE + difficultyBonus + streakBonus;
    }

    private int calculatePenaltyDamage(Question question) {
        int difficultyModifier = question.getDifficulty().getLevel();
        return BASE_PENALTY_DAMAGE + (difficultyModifier * 5);
    }

    private int calculatePoints(Question question) {
        int basePoints = question.getPoints();
        int streakMultiplier = Math.min(currentStreak, 5);
        return basePoints * (1 + streakMultiplier / 10);
    }

    public void resetStreak() {
        this.currentStreak = 0;
    }

    public void reset() {
        this.currentStreak = 0;
        this.maxStreak = 0;
    }

    // Getters

    public int getCurrentStreak() {
        return currentStreak;
    }

    public int getMaxStreak() {
        return maxStreak;
    }

    public static class EvaluationResult {
        private final boolean correct;
        private final int damageToEnemy;
        private final int damageToPlayer;
        private final int pointsEarned;
        private final int streak;

        public EvaluationResult(boolean correct, int damageToEnemy, int damageToPlayer,
                                int pointsEarned, int streak) {
            this.correct = correct;
            this.damageToEnemy = damageToEnemy;
            this.damageToPlayer = damageToPlayer;
            this.pointsEarned = pointsEarned;
            this.streak = streak;
        }

        public boolean isCorrect() {
            return correct;
        }

        public int getDamageToEnemy() {
            return damageToEnemy;
        }

        public int getDamageToPlayer() {
            return damageToPlayer;
        }

        public int getPointsEarned() {
            return pointsEarned;
        }

        public int getStreak() {
            return streak;
        }
    }
}