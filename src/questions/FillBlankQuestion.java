package questions;

import java.util.ArrayList;
import java.util.List;

/**
 * Pergunta de completar lacuna.
 * O jogador deve digitar a palavra que completa corretamente a frase.
 *
 * Demonstra: Herança (extends Question) e Polimorfismo
 */
public class FillBlankQuestion extends Question {
    private List<String> acceptedAnswers;

    /**
     * Construtor da pergunta de completar lacuna.
     *
     * @param statement Enunciado com a lacuna (use ____ para indicar a lacuna)
     * @param category Categoria
     * @param difficulty Dificuldade
     * @param correctAnswer Resposta correta principal
     * @param alternativeAnswers Respostas alternativas também aceitas
     */
    public FillBlankQuestion(String statement, Category category, Difficulty difficulty,
                             String correctAnswer, String... alternativeAnswers) {
        super(statement, category, difficulty, correctAnswer);
        this.acceptedAnswers = new ArrayList<>();
        this.acceptedAnswers.add(correctAnswer);
        for (String alt : alternativeAnswers) {
            this.acceptedAnswers.add(alt);
        }
    }

    /**
     * Verifica se a resposta está correta.
     * Ignora maiúsculas/minúsculas e espaços extras, e aceita respostas alternativas.
     *
     * @param answer Resposta do jogador
     * @return true se correta
     */
    @Override
    public boolean verificarResposta(String answer) {
        if (answer == null) return false;

        String normalized = normalize(answer);

        for (String accepted : acceptedAnswers) {
            if (normalize(accepted).equals(normalized)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Normaliza o texto removendo acentos, espaços extras e convertendo para minúsculas.
     */
    private String normalize(String text) {
        text = text.trim().toLowerCase();
        // Remove acentos comuns
        text = text.replaceAll("[áàâã]", "a")
                .replaceAll("[éèê]", "e")
                .replaceAll("[íìî]", "i")
                .replaceAll("[óòôõ]", "o")
                .replaceAll("[úùû]", "u")
                .replaceAll("ç", "c");
        return text;
    }

    /**
     * Exibe a pergunta formatada com a lacuna destacada.
     *
     * @return String formatada
     */
    @Override
    public String display() {
        StringBuilder sb = new StringBuilder();
        sb.append(getHeader()).append("\n");
        sb.append("║                                                               ║\n");
        sb.append("║  ✏️  Complete a lacuna:                                       ║\n");
        sb.append("║                                                               ║\n");

        // Quebra o enunciado em linhas de 60 caracteres
        String[] lines = wrapText(statement, 60);
        for (String line : lines) {
            sb.append("║  ").append(line);
            sb.append(padSpaces(62 - line.length())).append("║\n");
        }

        sb.append("║                                                               ║\n");
        sb.append("╠═══════════════════════════════════════════════════════════════╣\n");
        sb.append("║  Digite a palavra que completa a frase.                       ║\n");
        sb.append("║                                                               ║\n");
        sb.append("╚═══════════════════════════════════════════════════════════════╝");

        return sb.toString();
    }

    /**
     * Quebra texto em linhas do tamanho especificado.
     */
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

    // Getter

    public List<String> getAcceptedAnswers() {
        return new ArrayList<>(acceptedAnswers);
    }
}