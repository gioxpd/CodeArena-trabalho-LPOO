package ui;

import enemies.Enemy;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Painel que desenha o inimigo atual: imagem grande, nome e barra de vida larga.
 */
public class EnemySpritePanel extends JPanel {

    private Enemy enemy;
    private final int spriteSize = 200;

    public EnemySpritePanel() {
        setOpaque(false);
        setPreferredSize(new Dimension(360, 300));
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (enemy == null) return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Layout ancorado na base: garante que nome e barra de vida fiquem
        // sempre visíveis mesmo quando o painel recebe menos altura.
        int barWidth = Math.min(300, w - 40);
        int barHeight = 22;
        int barX = (w - barWidth) / 2;
        int barY = h - barHeight - 12;

        int availableSpriteHeight = barY - 16;

        int drawSize = Math.max(80, Math.min(spriteSize, availableSpriteHeight));
        int spriteX = (w - drawSize) / 2;
        int spriteY = Math.max(4, (barY - drawSize) / 2);

        // Imagem do inimigo
        ImageIcon icon = ImageManager.getEnemyImage(enemy.getStage(), drawSize, drawSize);
        if (icon != null) {
            g2.drawImage(icon.getImage(), spriteX, spriteY, this);
        } else {
            g2.setColor(Theme.PANEL_LIGHT);
            g2.fillRoundRect(spriteX, spriteY, drawSize, drawSize, 16, 16);
        }

        g2.setColor(Theme.PANEL);
        g2.fillRoundRect(barX, barY, barWidth, barHeight, 10, 10);

        double ratio = Math.max(0, (double) enemy.getHp() / enemy.getMaxHp());
        g2.setColor(Theme.ENEMY);
        g2.fillRoundRect(barX, barY, (int) (barWidth * ratio), barHeight, 10, 10);

        // Texto HP centralizado na barra
        g2.setFont(Theme.BODY_BOLD);
        g2.setColor(Theme.TEXT);
        String hpText = enemy.getHp() + " / " + enemy.getMaxHp() + " HP";
        int hpTextWidth = g2.getFontMetrics().stringWidth(hpText);
        g2.drawString(hpText, (w - hpTextWidth) / 2, barY + 16);

        g2.dispose();
    }
}