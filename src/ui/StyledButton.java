package ui;

import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Botão estilizado com o tema do jogo, usado em todas as telas.
 */
public class StyledButton extends JButton {

    private Color baseColor;
    private Color hoverColor;

    public StyledButton(String text) {
        this(text, Theme.PRIMARY, Theme.PRIMARY_DARK);
    }

    public StyledButton(String text, Color base, Color hover) {
        super(text);
        this.baseColor = base;
        this.hoverColor = hover;

        setFont(Theme.BODY_BOLD);
        setForeground(new Color(0x1A1D2E));
        setBackground(base);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(true);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEmptyBorder(12, 22, 12, 22));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (isEnabled()) setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (isEnabled()) setBackground(baseColor);
            }
        });
    }

    public void setColors(Color base, Color hover) {
        this.baseColor = base;
        this.hoverColor = hover;
        setBackground(base);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE, getPreferredSize().height);
    }
}