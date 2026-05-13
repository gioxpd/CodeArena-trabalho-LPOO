package battle;

import questions.AnswerEvaluator.EvaluationResult;
import questions.Question;

public class Round {
    private int roundNumber;
    private Question question;
    private String playerAnswer;
    private EvaluationResult result;
    private long startTime;
    private long endTime;

    /**
     * Construtor da rodada.
     *
     * @param roundNumber Número da rodada
     */
    public Round(int roundNumber) {
        this.roundNumber = roundNumber;
        this.startTime = System.currentTimeMillis();
    }

    public void endRound() {
        this.endTime = System.currentTimeMillis();
    }

    public double getDurationInSeconds() {
        if (endTime == 0) {
            endTime = System.currentTimeMillis();
        }
        return (endTime - startTime) / 1000.0;
    }

    public boolean wasCorrect() {
        return result != null && result.isCorrect();
    }

    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("Rodada ").append(roundNumber).append(": ");

        if (question != null) {
            sb.append(question.getCategory().getIcon()).append(" ");
            sb.append(question.getDifficulty().getIcon()).append(" ");
        }

        if (result != null) {
            sb.append(result.isCorrect() ? "✅" : "❌");
            if (result.isCorrect()) {
                sb.append(" (+").append(result.getPointsEarned()).append(" pts)");
            }
        }

        sb.append(" [").append(String.format("%.1f", getDurationInSeconds())).append("s]");

        return sb.toString();
    }

    // Getters e Setters

    public int getRoundNumber() {
        return roundNumber;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getPlayerAnswer() {
        return playerAnswer;
    }

    public void setPlayerAnswer(String playerAnswer) {
        this.playerAnswer = playerAnswer;
    }

    public EvaluationResult getResult() {
        return result;
    }

    public void setResult(EvaluationResult result) {
        this.result = result;
        endRound();
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }
}