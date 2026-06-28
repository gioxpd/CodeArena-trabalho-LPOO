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
 * São quatro faixas, todas em "assets/audio/":
 *   - background.mp3 : música de fundo das batalhas normais, em loop
 *   - boss.mp3       : música de fundo da batalha do chefe final, em loop
 *   - victory.mp3    : tocada uma vez na tela de vitória
 *   - defeat.mp3     : tocada uma vez na tela de derrota
 *
 * Cada faixa roda em sua própria thread (daemon) para não travar a
 * interface gráfica (Swing).
 */
public final class SoundManager {

    private static final String BASE_PATH = "assets/audio/";

    public static final String TRACK_BATTLE = "background.mp3";
    public static final String TRACK_BOSS = "boss.mp3";

    private static AdvancedPlayer backgroundPlayer;
    private static Thread backgroundThread;
    private static volatile boolean backgroundLooping;
    private static String currentTrack;

    private static AdvancedPlayer effectPlayer;
    private static Thread effectThread;

    private SoundManager() {
        // Classe utilitária, não deve ser instanciada.
    }

    /**
     * Inicia a música de fundo da batalha normal em loop.
     */
    public static void playBattleLoop() {
        playBackgroundLoop(TRACK_BATTLE);
    }

    /**
     * Inicia a música de fundo do chefe final em loop.
     */
    public static void playBossLoop() {
        playBackgroundLoop(TRACK_BOSS);
    }

    /**
     * Inicia uma música de fundo em loop contínuo.
     * Se a faixa pedida já estiver tocando, nada acontece (evita reiniciar
     * a música a cada atualização de tela). Trocar de faixa interrompe a anterior.
     */
    public static synchronized void playBackgroundLoop(String fileName) {
        if (backgroundLooping && fileName.equals(currentTrack)) {
            return;
        }
        stopBackground();

        final File file = resolve(fileName);
        if (file == null) {
            return;
        }

        currentTrack = fileName;
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
        currentTrack = null;
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
