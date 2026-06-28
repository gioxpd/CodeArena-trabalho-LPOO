package ui;

import audio.SoundManager;
import characters.Character;
import questions.FillBlankQuestion;
import questions.MultipleChoiceQuestion;
import questions.Question;
import questions.TrueFalseQuestion;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Tela de batalha: mostra o inimigo, os heróis, a pergunta atual e
 * os controles de resposta. Atualiza-se a cada turno conforme o jogador
 * interage, exibindo um log de combate.
 */
public class BattlePanel extends JPanel {

    private final GameWindow window;
    private final GameController controller;

    private final EnemySpritePanel enemyPanel = new EnemySpritePanel();
    private final JPanel heroRow = new JPanel();
    private final List<HeroSpritePanel> heroPanels = new ArrayList<>();

    private final JLabel stageLabel = new JLabel();
    private final JLabel scoreLabel = new JLabel();
    private final JLabel categoryLabel = new JLabel();
    private final JTextArea questionArea = new JTextArea();
    private final JPanel answerPanel = new JPanel();
    private final JTextArea logArea = new JTextArea();

    private final StyledButton abilityButton = new StyledButton("Usar Habilidade");

    // Cores e timer para o efeito de piscar quando a habilidade está disponível
    private static final Color ABILITY_BASE = new Color(0x6A4FA8);
    private static final Color ABILITY_HOVER = new Color(0x533D85);
    private static final Color ABILITY_BLINK = new Color(0xF2C94C);
    private Timer abilityBlinkTimer;
    private boolean blinkOn = false;

    public BattlePanel(GameWindow window, GameController controller) {
        this.window = window;
        this.controller = controller;

        setBackground(Theme.BACKGROUND);
        setLayout(new BorderLayout(0, 0));

        add(buildTopBar(), BorderLayout.NORTH);
        add(buildArena(), BorderLayout.CENTER);
        add(buildInteractionArea(), BorderLayout.SOUTH);
    }

    // ----- Construção dos componentes -----

    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(Theme.PANEL);
        bar.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        stageLabel.setFont(Theme.SUBHEADING);
        stageLabel.setForeground(Theme.PRIMARY);
        bar.add(stageLabel, BorderLayout.WEST);

        scoreLabel.setFont(Theme.SUBHEADING);
        scoreLabel.setForeground(Theme.TEXT);
        bar.add(scoreLabel, BorderLayout.EAST);

        return bar;
    }

    private JPanel buildArena() {
        JPanel arena = new JPanel(new BorderLayout());
        arena.setOpaque(false);
        arena.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Inimigo no topo
        arena.add(enemyPanel, BorderLayout.CENTER);

        // Fileira de heróis na base
        heroRow.setOpaque(false);
        heroRow.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        for (Character hero : controller.getPlayer().getHeroes()) {
            HeroSpritePanel hp = new HeroSpritePanel(hero);
            heroPanels.add(hp);
            heroRow.add(hp);
        }
        arena.add(heroRow, BorderLayout.SOUTH);

        return arena;
    }

    private JPanel buildInteractionArea() {
        JPanel container = new JPanel(new BorderLayout(16, 0));
        container.setBackground(Theme.PANEL);
        container.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));
        container.setPreferredSize(new Dimension(1000, 250));

        // Lado esquerdo: pergunta + respostas
        JPanel left = new JPanel(new BorderLayout(0, 10));
        left.setOpaque(false);

        categoryLabel.setFont(Theme.SMALL);
        categoryLabel.setForeground(Theme.PRIMARY);
        left.add(categoryLabel, BorderLayout.NORTH);

        questionArea.setEditable(false);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setFont(Theme.BODY_BOLD);
        questionArea.setBackground(Theme.PANEL);
        questionArea.setForeground(Theme.TEXT);
        questionArea.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
        left.add(questionArea, BorderLayout.CENTER);

        answerPanel.setOpaque(false);
        answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.Y_AXIS));
        left.add(answerPanel, BorderLayout.SOUTH);

        container.add(left, BorderLayout.CENTER);

        // Lado direito: log de combate + botão de habilidade
        JPanel right = new JPanel(new BorderLayout(0, 10));
        right.setOpaque(false);
        right.setPreferredSize(new Dimension(320, 220));

        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setFont(Theme.LOG);
        logArea.setBackground(Theme.BACKGROUND);
        logArea.setForeground(Theme.TEXT);
        logArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JScrollPane scroll = new JScrollPane(logArea);
        scroll.setBorder(BorderFactory.createLineBorder(Theme.PANEL_LIGHT));
        right.add(scroll, BorderLayout.CENTER);

        abilityButton.setColors(ABILITY_BASE, ABILITY_HOVER);
        abilityButton.setForeground(Theme.TEXT);
        abilityButton.setEnabled(false);
        abilityButton.addActionListener(e -> onUseAbility());
        right.add(abilityButton, BorderLayout.SOUTH);

        container.add(right, BorderLayout.EAST);

        return container;
    }

    // ----- Atualização de estado -----

    public void refreshAll() {
        updateBattleMusic();
        enemyPanel.setEnemy(controller.getEnemy());
        for (HeroSpritePanel hp : heroPanels) {
            hp.repaint();
        }
        stageLabel.setText("Estágio " + controller.getCurrentStage() + " / " + GameController.MAX_STAGES
                + "      Rodada " + controller.getRoundNumber());
        scoreLabel.setText("Pontuação: " + controller.getScoreSystem().getTotalScore());
        updateAbilityButton();
        updateAttackerHighlight();
        showQuestion(controller.getCurrentQuestion());
    }

    /**
     * Escolhe a música de fundo conforme o estágio: o estágio final (chefe)
     * usa a faixa do chefão; os demais usam a faixa de batalha normal.
     * O SoundManager ignora o pedido se a faixa correta já estiver tocando,
     * então isso pode ser chamado a cada atualização sem reiniciar a música.
     */
    private void updateBattleMusic() {
        if (controller.getCurrentStage() >= GameController.MAX_STAGES) {
            SoundManager.playBossLoop();
        } else {
            SoundManager.playBattleLoop();
        }
    }

    private void updateAttackerHighlight() {
        for (HeroSpritePanel hp : heroPanels) {
            hp.setHighlighted(false);
        }
    }

    /**
     * Habilita o botão de habilidade e faz com que ele pisque enquanto
     * a habilidade especial estiver disponível, para chamar a atenção.
     */
    private void updateAbilityButton() {
        boolean available = controller.canUseAbility();
        abilityButton.setEnabled(available);

        if (available) {
            if (abilityBlinkTimer == null) {
                abilityBlinkTimer = new Timer(450, e -> {
                    blinkOn = !blinkOn;
                    abilityButton.setColors(blinkOn ? ABILITY_BLINK : ABILITY_BASE, ABILITY_HOVER);
                    abilityButton.setForeground(blinkOn ? new Color(0x1A1D2E) : Theme.TEXT);
                });
                abilityBlinkTimer.start();
            }
        } else {
            if (abilityBlinkTimer != null) {
                abilityBlinkTimer.stop();
                abilityBlinkTimer = null;
            }
            blinkOn = false;
            abilityButton.setColors(ABILITY_BASE, ABILITY_HOVER);
            abilityButton.setForeground(Theme.TEXT);
        }
    }

    /**
     * Exibe a pergunta atual e gera os controles de resposta adequados ao tipo.
     */
    private void showQuestion(Question question) {
        categoryLabel.setText(question.getCategory().getDisplayName().toUpperCase()
                + "  •  " + question.getDifficulty().getDisplayName());
        questionArea.setText(question.getStatement());

        answerPanel.removeAll();

        if (question instanceof MultipleChoiceQuestion) {
            buildMultipleChoice((MultipleChoiceQuestion) question);
        } else if (question instanceof TrueFalseQuestion) {
            buildTrueFalse();
        } else if (question instanceof FillBlankQuestion) {
            buildFillBlank();
        }

        answerPanel.revalidate();
        answerPanel.repaint();
    }

    private void buildMultipleChoice(MultipleChoiceQuestion q) {
        JPanel grid = new JPanel(new GridLayout(0, 2, 10, 10));
        grid.setOpaque(false);
        List<String> options = q.getOptions();
        char letter = 'A';
        for (String option : options) {
            final String answer = String.valueOf(letter);
            StyledButton btn = new StyledButton(letter + ") " + option,
                    Theme.PANEL_LIGHT, Theme.PRIMARY_DARK);
            btn.setForeground(Theme.TEXT);
            btn.addActionListener(e -> handleAnswer(answer));
            grid.add(btn);
            letter++;
        }
        answerPanel.add(grid);
    }

    private void buildTrueFalse() {
        JPanel row = new JPanel(new GridLayout(1, 2, 10, 0));
        row.setOpaque(false);

        StyledButton trueBtn = new StyledButton("Verdadeiro", Theme.HERO, new Color(0x4A874A));
        trueBtn.setForeground(Theme.TEXT);
        trueBtn.addActionListener(e -> handleAnswer("V"));
        row.add(trueBtn);

        StyledButton falseBtn = new StyledButton("Falso", Theme.ENEMY, new Color(0xA83C3C));
        falseBtn.setForeground(Theme.TEXT);
        falseBtn.addActionListener(e -> handleAnswer("F"));
        row.add(falseBtn);

        answerPanel.add(row);
    }

    private void buildFillBlank() {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(false);

        JTextField field = new JTextField();
        field.setFont(Theme.BODY);
        field.setBackground(Theme.BACKGROUND);
        field.setForeground(Theme.TEXT);
        field.setCaretColor(Theme.TEXT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.PANEL_LIGHT),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));

        StyledButton submit = new StyledButton("Responder");
        Runnable send = () -> {
            String text = field.getText();
            if (text != null && !text.trim().isEmpty()) {
                handleAnswer(text.trim());
            }
        };
        submit.addActionListener(e -> send.run());
        field.addActionListener(e -> send.run());

        row.add(field, BorderLayout.CENTER);
        row.add(submit, BorderLayout.EAST);
        answerPanel.add(row);
    }

    // ----- Lógica de turno -----

    private void handleAnswer(String answer) {
        setAnswersEnabled(false);
        GameController.TurnResult turn = controller.submitAnswer(answer);
        appendLog(formatTurn(turn));
        refreshSpritesAndStatus();

        if (turn.enemyDefeated) {
            handleVictory();
        } else if (turn.partyDefeated) {
            handleDefeat();
        } else {
            // Pequena pausa para o jogador ler o resultado, depois nova pergunta
            Timer timer = new Timer(900, e -> {
                controller.nextQuestion();
                refreshAll();
                setAnswersEnabled(true);
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void refreshSpritesAndStatus() {
        enemyPanel.setEnemy(controller.getEnemy());
        for (HeroSpritePanel hp : heroPanels) {
            hp.repaint();
        }
        scoreLabel.setText("Pontuação: " + controller.getScoreSystem().getTotalScore());
        updateAbilityButton();
    }

    private void onUseAbility() {
        List<Character> alive = controller.getPlayer().getAliveHeroes();
        if (alive.isEmpty()) return;

        String[] options = new String[alive.size()];
        for (int i = 0; i < alive.size(); i++) {
            options[i] = alive.get(i).getName() + " (" + alive.get(i).getCharacterClass() + ")";
        }

        int choice = JOptionPane.showOptionDialog(this,
                "Escolha um herói para usar a habilidade especial:",
                "Habilidade Especial",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if (choice >= 0) {
            Character hero = alive.get(choice);
            String result = controller.useAbility(hero);
            appendLog("[Habilidade] " + result);
            appendLog(">> " + hero.getName() + " está preparado para o próximo ataque!");
            refreshSpritesAndStatus();
            // Destaca o herói que vai atacar
            for (HeroSpritePanel hp : heroPanels) {
                hp.setHighlighted(hp.getHero() == hero);
            }
        }
    }

    private void handleVictory() {
        appendLog("=== " + controller.getEnemy().getName() + " foi derrotado! ===");
        boolean hasNext = controller.advanceStage();
        if (hasNext) {
            JOptionPane.showMessageDialog(this,
                    "Vitória! Seu grupo recuperou parte da vida.\nPrepare-se para o próximo desafio!",
                    "Estágio Concluído", JOptionPane.INFORMATION_MESSAGE);
            refreshAll();
            setAnswersEnabled(true);
        } else {
            showGameOver(true);
        }
    }

    private void handleDefeat() {
        appendLog("=== Seu grupo foi derrotado... ===");
        showGameOver(false);
    }

    private void showGameOver(boolean victory) {
        window.showGameOver(victory);
    }

    // ----- Utilidades -----

    private void setAnswersEnabled(boolean enabled) {
        for (Component c : answerPanel.getComponents()) {
            setEnabledRecursive(c, enabled);
        }
    }

    private void setEnabledRecursive(Component c, boolean enabled) {
        c.setEnabled(enabled);
        if (c instanceof java.awt.Container) {
            for (Component child : ((java.awt.Container) c).getComponents()) {
                setEnabledRecursive(child, enabled);
            }
        }
    }

    private void appendLog(String text) {
        logArea.append(text + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    private String formatTurn(GameController.TurnResult turn) {
        StringBuilder sb = new StringBuilder();
        if (turn.correct) {
            sb.append("CORRETO! ");
            if (turn.attackerName != null) {
                sb.append(turn.attackerName).append(" causou ")
                        .append(turn.damageToEnemy).append(" de dano");
            }
            if (turn.mageBonus) sb.append(" (bônus mágico!)");
            sb.append(". +").append(turn.pointsEarned).append(" pts");
            if (turn.streak > 1) sb.append("  [sequência ").append(turn.streak).append("]");
        } else {
            sb.append("ERROU! Resposta certa: ").append(turn.correctAnswerText).append(". ");
            if (turn.protectorName != null) {
                sb.append(turn.protectorName).append(" absorveu parte do dano. ");
            }
            if (!turn.hitTargets.isEmpty()) {
                sb.append("Atingidos: ").append(String.join(", ", turn.hitTargets));
            }
            for (String defeated : turn.defeatedHeroes) {
                sb.append("\n  ").append(defeated).append(" foi derrotado!");
            }
        }
        return sb.toString();
    }
}
