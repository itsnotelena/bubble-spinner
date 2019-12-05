import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import config.Config;
import game.BubbleSpinner;

/**
 * Launcher class.
 * This is the starting point of the game
 * which calls the game library.
 */
public class Launcher {
    /**
     * Main class for the Launcher.
     * @param args no arguments needed.
     */
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle(Config.Game.TITLE);
        config.setWindowedMode(Config.Game.WIDTH, Config.Game.HEIGHT);
        config.setWindowIcon(Config.Game.ICON);
        config.setResizable(false);
        new Lwjgl3Application(new BubbleSpinner(), config);
    }
}
