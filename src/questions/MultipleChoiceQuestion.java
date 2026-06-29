package questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultipleChoiceQuestion extends Question {
    private List<String> options;
    private int correctIndex;


    public MultipleChoiceQuestion(String statement, Category category, Difficulty difficulty,
                                  String correctAnswer, List<String> options) {
        super(statement, category, difficulty, correctAnswer);
        this.options = new ArrayList<>(options);
        this.correctIndex = options.indexOf(correctAnswer);
    }

    public MultipleChoiceQuestion(String statement, Category category, Difficulty difficulty,
                                  String correctAnswer, String... wrongOptions) {
        super(statement, category, difficulty, correctAnswer);
        this.options = new ArrayList<>();
        this.options.add(correctAnswer);
        for (String option : wrongOptions) {
            this.options.add(option);
        }
        shuffleOptions();
    }


    private void shuffleOptions() {
        Collections.shuffle(options);
        correctIndex = options.indexOf(correctAnswer);
    }

    @Override
    public boolean verificarResposta(String answer) {
        answer = answer.trim().toUpperCase();

        // Verifica se é uma letra (A, B, C, D)
        if (answer.length() == 1 && answer.charAt(0) >= 'A' && answer.charAt(0) <= 'Z') {
            int index = answer.charAt(0) - 'A';
            return index == correctIndex;
        }

        // Verifica se é um número
        try {
            int index = Integer.parseInt(answer) - 1;
            return index == correctIndex;
        } catch (NumberFormatException e) {
            // Ignora e tenta comparar texto
        }

        // Compara diretamente com a resposta correta
        return answer.equalsIgnoreCase(correctAnswer);
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

        // Exibe as opções
        char letter = 'A';
        for (String option : options) {
            String optionText = letter + ") " + option;
            if (optionText.length() > 60) {
                optionText = optionText.substring(0, 57) + "...";
            }
            sb.append("║  ").append(optionText);
            sb.append(padSpaces(62 - optionText.length())).append("║\n");
            letter++;
        }

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

    public String getCorrectAnswerFormatted() {
        char letter = (char) ('A' + correctIndex);
        return letter + ") " + correctAnswer;
    }

    public List<String> getOptions() {
        return new ArrayList<>(options);
    }

    public int getCorrectIndex() {
        return correctIndex;
    }
}