package ui;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;

/**
 * Tela de fim de jogo (vitória ou derrota), exibindo as estatísticas finais.
 */
public class GameOverPanel extends JPanel {

    public GameOverPanel(GameWindow window, GameController controller, boolean victory) {
        setBackground(Theme.BACKGROUND);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));

        add(Box.createVerticalGlue());

        JLabel title = new JLabel(victory ? "VITÓRIA!" : "DERROTA");
        title.setFont(Theme.TITLE);
        title.setForeground(victory ? Theme.PRIMARY : Theme.ENEMY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);

        String message = victory
                ? "Você completou todas as batalhas! O conhecimento é a maior arma."
                : "Seu grupo foi derrotado, mas o aprendizado continua.";
        JLabel subtitle = new JLabel(message);
        subtitle.setFont(Theme.BODY);
        subtitle.setForeground(Theme.TEXT_MUTED);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));
        add(subtitle);

        // Estatísticas
        JPanel stats = new JPanel();
        stats.setOpaque(false);
        stats.setLayout(new BoxLayout(stats, BoxLayout.Y_AXIS));
        stats.setMaximumSize(new Dimension(400, 220));
        stats.setAlignmentX(Component.CENTER_ALIGNMENT);

        var score = controller.getScoreSystem();
        addStat(stats, "Jogador", controller.getPlayer().getName());
        addStat(stats, "Estágios completados",
                (victory ? GameController.MAX_STAGES : controller.getCurrentStage() - 1)
                        + " / " + GameController.MAX_STAGES);
        addStat(stats, "Pontuação final", String.valueOf(score.getTotalScore()));
        addStat(stats, "Respostas certas", String.valueOf(score.getCorrectAnswers()));
        addStat(stats, "Respostas erradas", String.valueOf(score.getWrongAnswers()));
        addStat(stats, "Maior sequência", String.valueOf(score.getMaxStreak()));
        addStat(stats, "Taxa de acerto", String.format("%.1f%%", score.getAccuracyRate()));

        add(stats);
        add(Box.createRigidArea(new Dimension(0, 36)));

        StyledButton menuBtn = new StyledButton("Voltar ao Menu");
        menuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuBtn.setMaximumSize(new Dimension(240, 48));
        menuBtn.addActionListener(e -> window.showMenu());
        add(menuBtn);

        add(Box.createVerticalGlue());
    }

    private void addStat(JPanel parent, String label, String value) {
        JPanel row = new JPanel();
        row.setOpaque(false);
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setMaximumSize(new Dimension(400, 30));

        JLabel l = new JLabel(label);
        l.setFont(Theme.BODY);
        l.setForeground(Theme.TEXT_MUTED);
        row.add(l);
        row.add(Box.createHorizontalGlue());

        JLabel v = new JLabel(value);
        v.setFont(Theme.BODY_BOLD);
        v.setForeground(Theme.TEXT);
        row.add(v);

        parent.add(row);
        parent.add(Box.createRigidArea(new Dimension(0, 8)));
    }
}