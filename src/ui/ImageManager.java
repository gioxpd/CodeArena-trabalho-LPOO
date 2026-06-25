package ui;

import characters.Character;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Gerencia o carregamento e cache das imagens dos personagens e inimigos.
 *
 * As imagens ficam na pasta "assets/images/" na raiz do projeto.
 * Caso uma imagem não seja encontrada, retorna null e a interface
 * desenha um espaço reservado.
 */
public class ImageManager {

    private static final String BASE_PATH = "assets/images/";
    private static final Map<String, ImageIcon> cache = new HashMap<>();

    /**
     * Retorna a imagem do herói com base na sua classe (Warrior, Healer, etc).
     *
     * @param hero   personagem herói
     * @param width  largura desejada
     * @param height altura desejada
     * @return ImageIcon redimensionado ou null se não encontrado
     */
    public static ImageIcon getHeroImage(Character hero, int width, int height) {
        String key = hero.getClass().getSimpleName().toLowerCase();
        return load(key + ".png", width, height);
    }

    /**
     * Retorna a imagem do inimigo com base no estágio (1 a 5).
     *
     * @param stage  estágio atual
     * @param width  largura desejada
     * @param height altura desejada
     * @return ImageIcon redimensionado ou null se não encontrado
     */
    public static ImageIcon getEnemyImage(int stage, int width, int height) {
        int clamped = Math.max(1, Math.min(5, stage));
        return load("enemy_stage" + clamped + ".png", width, height);
    }

    /**
     * Carrega e redimensiona uma imagem, usando cache.
     */
    private static ImageIcon load(String fileName, int width, int height) {
        String cacheKey = fileName + "_" + width + "x" + height;
        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }

        File file = new File(BASE_PATH + fileName);
        if (!file.exists()) {
            cache.put(cacheKey, null);
            return null;
        }

        ImageIcon original = new ImageIcon(file.getAbsolutePath());
        Image scaled = original.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaled);
        cache.put(cacheKey, icon);
        return icon;
    }

    /**
     * Cria uma imagem reserva (placeholder) caso a real não exista.
     * Não utilizado diretamente, mantido para extensibilidade.
     */
    public static BufferedImage createPlaceholder(int width, int height) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }
}