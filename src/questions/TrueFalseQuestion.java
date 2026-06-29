package questions;

import java.util.ArrayList;
import java.util.List;


public class TrueFalseQuestion extends Question {
    private boolean correctAnswerBoolean;

    public TrueFalseQuestion(String statement, Category category, Difficulty difficulty, boolean isTrue) {
        super(statement, category, difficulty, isTrue ? "Verdadeiro" : "Falso");
        this.correctAnswerBoolean = isTrue;
    }

    @Override
    public boolean verificarResposta(String answer) {
        answer = answer.trim().toUpperCase();

        boolean playerAnswer;

        // Aceita múltiplos formatos
        switch (answer) {
            case "V":
            case "VERDADEIRO":
            case "TRUE":
            case "T":
            case "1":
            case "SIM":
            case "S":
                playerAnswer = true;
                break;
            case "F":
            case "FALSO":
            case "FALSE":
            case "2":
            case "NAO":
            case "NÃO":
            case "N":
                playerAnswer = false;
                break;
            default:
                return false;
        }

        return playerAnswer == correctAnswerBoolean;
    }

    @Override
    public String display() {
        StringBuilder sb = new StringBuilder();
        sb.append(getHeader()).append("\n");
        sb.append("║                                                               ║\n");

        // Quebra o enunciado em linhas de 60 caracteres
        String[] lines = wrapText(statement, 60);
        for (String line : lines) {
            sb.append("║  ").append(line);
            sb.append(padSpaces(62 - line.length())).append("║\n");
        }

        sb.append("║                                                               ║\n");
        sb.append("╠═══════════════════════════════════════════════════════════════╣\n");
        sb.append("║  1) ✅ Verdadeiro                                             ║\n");
        sb.append("║  2) ❌ Falso                                                   ║\n");
        sb.append("║                                                               ║\n");
        sb.append("╚═══════════════════════════════════════════════════════════════╝");

        return sb.toString();
    }

    private String[] wrapText(String text, int maxLength) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            if (currentLine.length() + word.length() + 1 <= maxLength) {
                if (currentLine.length() > 0) {
                    currentLine.append(" ");
                }
                currentLine.append(word);
            } else {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            }
        }

        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines.toArray(new String[0]);
    }

    public boolean isCorrectAnswerTrue() {
        return correctAnswerBoolean;
    }
}