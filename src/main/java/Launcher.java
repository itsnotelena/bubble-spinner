import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import config.Config;
import game.BubbleSpinner;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import server.Server;


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
    public static void main(String[] args) throws FileNotFoundException, SQLException {
        if (Config.Api.URL.contains("localhost")) {
            Server.main(new String[]{"database"});
        }
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle(Config.Game.TITLE);
        config.setWindowedMode(Config.Game.WIDTH, Config.Game.HEIGHT);
        config.setWindowIcon(Config.Game.ICON);
        config.setResizable(false);
        new Lwjgl3Application(new BubbleSpinner(), config);
    }
}
