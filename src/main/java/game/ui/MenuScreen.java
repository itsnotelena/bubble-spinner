package game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import game.BubbleSpinner;
import game.GameSettings;

public class MenuScreen extends ScreenAdapter {

    private transient Stage stage;
    private transient BubbleSpinner game;
    private transient TextButton loggedIn;
    private transient Leaderboard leaderboard;
    private transient MainMenu mainMenu;

    /**
     * Login Screen.
     * @param game BubbleSpinner instance.
     */

    public MenuScreen(BubbleSpinner game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        mainMenu = new MainMenu(stage, this);

        leaderboard = new Leaderboard();
        leaderboard.setPosition(
                Gdx.graphics.getWidth() * 6 / 8.f,
                4 * Gdx.graphics.getHeight() / 8.f
        );
        stage.addActor(leaderboard);

        loggedIn = UserInterfaceFactory.createTextButton("Player : "
                                    + game.getUser().getUsername());
        loggedIn.setPosition(Gdx.graphics.getWidth() / 8.f,
                7 * Gdx.graphics.getHeight() / 8.f);
        stage.addActor(loggedIn);

    }

    @Override
    public void resize(int w, int h) {
        stage.getViewport().update(w,h,true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

        // Space -> Start Game
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            startGame(mainMenu.getGameSettings());
        }

        // C -> Computer Player
        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            mainMenu.switchComputerPlayer();
        }

        // T -> Timer
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            mainMenu.incrementTimer();
        }

        // D -> Difficulty
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            mainMenu.changeDifficulty();
        }

        // A -> Achievements
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            achievementPanel();
        }

        // M -> Change mode
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            mainMenu.changeMode();
        }

        // Backspace -> Logout
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
            logout();
        }

        // Escape -> Exit
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            dispose();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        game.dispose();
    }

    /**
     * Logout from the game.
     */
    public void logout() {
        game.setScreen(new LoginScreen(game));
        stage.dispose();
    }

    /**
     * Go to the achievement screen.
     */
    public void achievementPanel() {
        game.setScreen(new AchievementScreen(game));
        stage.dispose();
    }

    /**
     * Move to the Game Screen.
     * @param gameSettings Settings to start the game with.
     */
    public void startGame(GameSettings gameSettings) {
        game.setScreen(new GameScreen(game, gameSettings));
        stage.dispose();
    }
}
