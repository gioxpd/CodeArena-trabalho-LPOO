package ui;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

/**
 * Tela de menu principal do jogo.
 * Permite iniciar uma nova aventura, ver instruções e conhecer os heróis.
 */
public class MenuPanel extends JPanel {

    private final GameWindow window;

    public MenuPanel(GameWindow window) {
        this.window = window;
        setBackground(Theme.BACKGROUND);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));

        add(Box.createVerticalGlue());

        JLabel title = new JLabel("CodeArena");
        title.setFont(Theme.TITLE);
        title.setForeground(Theme.PRIMARY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);

        JLabel subtitle = new JLabel("Batalha do Conhecimento");
        subtitle.setFont(Theme.HEADING);
        subtitle.setForeground(Theme.TEXT);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setBorder(BorderFactory.createEmptyBorder(8, 0, 6, 0));
        add(subtitle);

        JLabel tagline = new JLabel("Um quiz educacional com batalhas em turnos");
        tagline.setFont(Theme.BODY);
        tagline.setForeground(Theme.TEXT_MUTED);
        tagline.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(tagline);

        add(Box.createRigidArea(new Dimension(0, 40)));

        JPanel buttons = new JPanel(new GridLayout(4, 1, 0, 14));
        buttons.setOpaque(false);
        buttons.setMaximumSize(new Dimension(320, 240));
        buttons.setAlignmentX(Component.CENTER_ALIGNMENT);

        StyledButton play = new StyledButton("Nova Aventura");
        play.addActionListener(e -> onPlay());
        buttons.add(play);

        StyledButton howTo = new StyledButton("Como Jogar", Theme.PANEL_LIGHT, Theme.PANEL);
        howTo.setForeground(Theme.TEXT);
        howTo.addActionListener(e -> showHowToPlay());
        buttons.add(howTo);

        StyledButton heroes = new StyledButton("Conhecer os Heróis", Theme.PANEL_LIGHT, Theme.PANEL);
        heroes.setForeground(Theme.TEXT);
        heroes.addActionListener(e -> showHeroes());
        buttons.add(heroes);

        StyledButton quit = new StyledButton("Sair", Theme.PANEL_LIGHT, Theme.PANEL);
        quit.setForeground(Theme.TEXT);
        quit.addActionListener(e -> System.exit(0));
        buttons.add(quit);

        add(buttons);
        add(Box.createVerticalGlue());
    }

    private void onPlay() {
        String name = JOptionPane.showInputDialog(this,
                "Qual é o seu nome, aventureiro?", "Nova Aventura",
                JOptionPane.QUESTION_MESSAGE);
        if (name == null) return; // cancelou
        if (name.trim().isEmpty()) {
            name = "Herói Anônimo";
        }
        window.startGame(name.trim());
    }

    private void showHowToPlay() {
        String text = "OBJETIVO\n" +
                "Derrote os inimigos respondendo perguntas corretamente!\n\n" +
                "BATALHA\n" +
                "- A cada rodada uma pergunta aparece na tela.\n" +
                "- Resposta certa: seu grupo ataca o inimigo.\n" +
                "- Resposta errada: o inimigo contra-ataca seu grupo.\n\n" +
                "HABILIDADES ESPECIAIS\n" +
                "- Acerte 2 perguntas seguidas para liberar uma habilidade.\n" +
                "- O herói escolhido para a habilidade ataca em seguida.\n\n" +
                "CATEGORIAS\n" +
                "História | Biologia | Programação | Curiosidades de Games\n\n" +
                "TIPOS DE PERGUNTA\n" +
                "Múltipla escolha | Verdadeiro ou Falso | Completar lacuna";
        JOptionPane.showMessageDialog(this, text, "Como Jogar", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showHeroes() {
        String text = "ASTYANAX - Guerreiro\n" +
                "DPS físico equilibrado. Habilidade: dobra o dano do próximo ataque.\n\n" +
                "FALLISTRA - Curandeira\n" +
                "Suporte e cura. Habilidade: recupera HP de todo o grupo.\n\n" +
                "FYNRALL - Mago\n" +
                "Dano mágico. Bônus de dano em perguntas difíceis.\n\n" +
                "MUDMUG - Arqueiro Goblin\n" +
                "Velocidade e precisão. Habilidade: crítico garantido.\n\n" +
                "EDRIUS - Golem Tank\n" +
                "Defesa altíssima. Habilidade: protege aliados por 2 turnos.";
        JOptionPane.showMessageDialog(this, text, "Conheça os Heróis", JOptionPane.INFORMATION_MESSAGE);
    }
}