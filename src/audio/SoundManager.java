package audio;

import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Gerencia a reprodução das músicas do jogo (arquivos MP3).
 *
 * Usa a biblioteca JLayer (lib/jlayer-1.0.1.jar) para decodificar MP3,
 * já que o Java puro não suporta esse formato nativamente.
 *
 * São três faixas, todas em "assets/audio/":
 *   - background.mp3 : música de fundo da batalha, tocada em loop
 *   - victory.mp3    : tocada uma vez na tela de vitória
 *   - defeat.mp3     : tocada uma vez na tela de derrota
 *
 * Cada faixa roda em sua própria thread (daemon) para não travar a
 * interface gráfica (Swing).
 */
public final class SoundManager {

    private static final String BASE_PATH = "assets/audio/";

    private static AdvancedPlayer backgroundPlayer;
    private static Thread backgroundThread;
    private static volatile boolean backgroundLooping;

    private static AdvancedPlayer effectPlayer;
    private static Thread effectThread;

    private SoundManager() {
        // Classe utilitária, não deve ser instanciada.
    }

    /**
     * Inicia a música de fundo da batalha em loop contínuo.
     * Se já houver uma música de fundo tocando, ela é reiniciada.
     */
    public static synchronized void playBackgroundLoop() {
        stopBackground();

        final File file = resolve("background.mp3");
        if (file == null) {
            return;
        }

        backgroundLooping = true;
        backgroundThread = new Thread(() -> {
            while (backgroundLooping) {
                try (InputStream in = new BufferedInputStream(new FileInputStream(file))) {
                    backgroundPlayer = new AdvancedPlayer(in);
                    backgroundPlayer.play();
                } catch (Exception e) {
                    System.err.println("[SoundManager] Erro ao tocar música de fundo: " + e.getMessage());
                    break;
                }
                // Ao terminar a faixa, o while reinicia a reprodução (loop),
                // a menos que stopBackground() tenha sido chamado.
            }
        }, "background-music");
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }

    /**
     * Para a música de fundo, caso esteja tocando.
     */
    public static synchronized void stopBackground() {
        backgroundLooping = false;
        if (backgroundPlayer != null) {
            backgroundPlayer.close();
            backgroundPlayer = null;
        }
        if (backgroundThread != null) {
            backgroundThread.interrupt();
            backgroundThread = null;
        }
    }

    /**
     * Toca a música de vitória uma única vez (sem loop).
     * A música de fundo é interrompida automaticamente.
     */
    public static void playVictory() {
        playEffectOnce("victory.mp3");
    }

    /**
     * Toca a música de derrota uma única vez (sem loop).
     * A música de fundo é interrompida automaticamente.
     */
    public static void playDefeat() {
        playEffectOnce("defeat.mp3");
    }

    /**
     * Para toda a reprodução de áudio (fundo e efeitos).
     */
    public static synchronized void stopAll() {
        stopBackground();
        stopEffect();
    }

    /**
     * Toca uma faixa única, interrompendo o fundo e qualquer efeito anterior.
     */
    private static synchronized void playEffectOnce(String fileName) {
        stopBackground();
        stopEffect();

        final File file = resolve(fileName);
        if (file == null) {
            return;
        }

        effectThread = new Thread(() -> {
            try (InputStream in = new BufferedInputStream(new FileInputStream(file))) {
                effectPlayer = new AdvancedPlayer(in);
                effectPlayer.play();
            } catch (Exception e) {
                System.err.println("[SoundManager] Erro ao tocar efeito " + fileName + ": " + e.getMessage());
            }
        }, "effect-music");
        effectThread.setDaemon(true);
        effectThread.start();
    }

    private static synchronized void stopEffect() {
        if (effectPlayer != null) {
            effectPlayer.close();
            effectPlayer = null;
        }
        if (effectThread != null) {
            effectThread.interrupt();
            effectThread = null;
        }
    }

    /**
     * Resolve o caminho de uma faixa. Retorna null (e avisa no console)
     * caso o arquivo não exista, para o jogo continuar sem áudio.
     */
    private static File resolve(String fileName) {
        File file = new File(BASE_PATH + fileName);
        if (!file.exists()) {
            System.err.println("[SoundManager] Arquivo de áudio não encontrado: " + file.getPath());
            return null;
        }
        return file;
    }
}
