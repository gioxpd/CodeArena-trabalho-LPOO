package ui;

import java.awt.Color;
import java.awt.Font;

/**
 * Paleta de cores e fontes centralizadas da interface gráfica.
 * Tema: RPG de fantasia sombria com destaque dourado.
 */
public final class Theme {

    private Theme() {}

    // Cores de fundo (neutros escuros)
    public static final Color BACKGROUND = new Color(0x1A1D2E);
    public static final Color PANEL = new Color(0x252A40);
    public static final Color PANEL_LIGHT = new Color(0x313752);

    // Cor primária (dourado/âmbar)
    public static final Color PRIMARY = new Color(0xE8B04B);
    public static final Color PRIMARY_DARK = new Color(0xB8862F);

    // Acentos
    public static final Color HERO = new Color(0x5FA85F);   // verde aliado
    public static final Color ENEMY = new Color(0xC84B4B);  // vermelho inimigo
    public static final Color DAMAGE = new Color(0xE05B5B);

    // Texto (neutros claros)
    public static final Color TEXT = new Color(0xE8E6E0);
    public static final Color TEXT_MUTED = new Color(0x9AA0B5);

    // Fontes
    public static final Font TITLE = new Font("Serif", Font.BOLD, 42);
    public static final Font HEADING = new Font("SansSerif", Font.BOLD, 22);
    public static final Font SUBHEADING = new Font("SansSerif", Font.BOLD, 16);
    public static final Font BODY = new Font("SansSerif", Font.PLAIN, 15);
    public static final Font BODY_BOLD = new Font("SansSerif", Font.BOLD, 15);
    public static final Font SMALL = new Font("SansSerif", Font.PLAIN, 12);
    public static final Font LOG = new Font("Monospaced", Font.PLAIN, 13);
}