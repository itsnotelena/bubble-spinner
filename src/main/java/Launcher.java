import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
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
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new BubbleSpinner(), config);
    }
}
