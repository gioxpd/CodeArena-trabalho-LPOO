package ui;

import characters.Character;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;

/**
 * Painel que desenha um herói: imagem (ou placeholder), nome e barra de vida.
 * Usado na fileira de heróis durante a batalha.
 */
public class HeroSpritePanel extends JPanel {

    private final Character hero;
    private boolean highlighted;
    private final int spriteSize = 90;

    public HeroSpritePanel(Character hero) {
        this.hero = hero;
        setOpaque(false);
        setPreferredSize(new Dimension(120, 170));
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
        repaint();
    }

    public Character getHero() {
        return hero;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        boolean alive = hero.isAlive();
        int spriteX = (w - spriteSize) / 2;
        int spriteY = 8;

        // Moldura de destaque (quando é o próximo atacante)
        if (highlighted && alive) {
            g2.setColor(Theme.PRIMARY);
            g2.setStroke(new BasicStroke(3f));
            g2.drawRoundRect(spriteX - 6, spriteY - 6, spriteSize + 12, spriteSize + 12, 14, 14);
        }

        // Estado crítico: menos da metade da vida (e ainda vivo)
        double hpRatio = Math.max(0, (double) hero.getHp() / hero.getMaxHp());
        boolean critical = hpRatio < 0.5;

        // Imagem do herói (usa a variante de dor quando em estado crítico)
        ImageIcon icon = ImageManager.getHeroImage(hero, spriteSize, spriteSize, critical);
        if (icon != null) {
            if (!alive) {
                // Esmaece heróis derrotados
                g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 0.3f));
            }
            g2.drawImage(icon.getImage(), spriteX, spriteY, this);
            g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 1f));
        } else {
            // Placeholder
            g2.setColor(Theme.PANEL_LIGHT);
            g2.fillRoundRect(spriteX, spriteY, spriteSize, spriteSize, 12, 12);
            g2.setColor(Theme.TEXT_MUTED);
            g2.drawString(hero.getClassIcon(), spriteX + spriteSize / 2 - 8, spriteY + spriteSize / 2);
        }

        // Nome
        g2.setFont(Theme.SMALL);
        String name = hero.getName();
        int nameWidth = g2.getFontMetrics().stringWidth(name);
        g2.setColor(alive ? Theme.TEXT : Theme.TEXT_MUTED);
        g2.drawString(name, (w - nameWidth) / 2, spriteY + spriteSize + 18);

        // Barra de vida
        int barY = spriteY + spriteSize + 26;
        int barWidth = spriteSize;
        int barHeight = 10;
        int barX = (w - barWidth) / 2;

        g2.setColor(Theme.PANEL_LIGHT);
        g2.fillRoundRect(barX, barY, barWidth, barHeight, 6, 6);

        double ratio = Math.max(0, (double) hero.getHp() / hero.getMaxHp());
        Color hpColor = ratio > 0.5 ? Theme.HERO : (ratio > 0.25 ? Theme.PRIMARY : Theme.ENEMY);
        g2.setColor(hpColor);
        g2.fillRoundRect(barX, barY, (int) (barWidth * ratio), barHeight, 6, 6);

        // Texto HP
        g2.setFont(Theme.SMALL);
        g2.setColor(Theme.TEXT);
        String hpText = hero.getHp() + "/" + hero.getMaxHp();
        int hpTextWidth = g2.getFontMetrics().stringWidth(hpText);
        g2.drawString(hpText, (w - hpTextWidth) / 2, barY + barHeight + 14);

        g2.dispose();
    }
}