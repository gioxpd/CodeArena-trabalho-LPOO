package ui;

import audio.SoundManager;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.CardLayout;
import java.awt.Dimension;

/**
 * Janela principal do jogo CodeArena.
 * Usa CardLayout para alternar entre as telas: Menu, Batalha e Fim de Jogo.
 *
 * Esta é a camada de interface gráfica (Swing) que substitui o antigo
 * modo console, reutilizando toda a lógica via GameController.
 */
public class GameWindow extends JFrame {

    private final CardLayout cardLayout;
    private final javax.swing.JPanel root;

    private final GameController controller;

    private MenuPanel menuPanel;
    private BattlePanel battlePanel;
    private GameOverPanel gameOverPanel;

    public static final String MENU = "MENU";
    public static final String BATTLE = "BATTLE";
    public static final String GAME_OVER = "GAME_OVER";

    public GameWindow() {
        super("CodeArena: Batalha do Conhecimento");
        this.controller = new GameController();
        this.cardLayout = new CardLayout();
        this.root = new javax.swing.JPanel(cardLayout);
        this.root.setBackground(Theme.BACKGROUND);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 720));
        setMinimumSize(new Dimension(900, 660));

        menuPanel = new MenuPanel(this);
        root.add(menuPanel, MENU);

        add(root);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Inicia uma nova partida e abre a tela de batalha.
     */
    public void startGame(String playerName) {
        controller.startNewGame(playerName);
        battlePanel = new BattlePanel(this, controller);
        root.add(battlePanel, BATTLE);
        cardLayout.show(root, BATTLE);
        battlePanel.refreshAll();
    }

    /**
     * Volta para o menu principal.
     */
    public void showMenu() {
        SoundManager.stopAll();
        if (battlePanel != null) {
            root.remove(battlePanel);
            battlePanel = null;
        }
        if (gameOverPanel != null) {
            root.remove(gameOverPanel);
            gameOverPanel = null;
        }
        cardLayout.show(root, MENU);
    }

    /**
     * Mostra a tela de fim de jogo (vitória ou derrota).
     */
    public void showGameOver(boolean victory) {
        if (gameOverPanel != null) {
            root.remove(gameOverPanel);
        }
        gameOverPanel = new GameOverPanel(this, controller, victory);
        root.add(gameOverPanel, GAME_OVER);
        cardLayout.show(root, GAME_OVER);
        if (victory) {
            SoundManager.playVictory();
        } else {
            SoundManager.playDefeat();
        }
    }

    public GameController getController() {
        return controller;
    }

    /**
     * Ponto de entrada da interface gráfica.
     */
    public static void launch() {
        SwingUtilities.invokeLater(() -> {
            GameWindow window = new GameWindow();
            window.setVisible(true);
        });
    }
}
