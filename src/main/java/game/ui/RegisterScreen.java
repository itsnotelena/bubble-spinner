package game.ui;

import client.Client;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import game.BubbleSpinner;
import game.GameSettings;
import server.User;

public class RegisterScreen extends ScreenAdapter {

    private transient Stage stage;
    private transient BubbleSpinner game;
    private transient PopupMenu popupMenu;
    private transient RegisterMenu registerMenu;

    /**
     * Registerss Screen.
     * @param game BubbleSpinner instance.
     */
    public RegisterScreen(BubbleSpinner game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        registerMenu = new RegisterMenu(stage, this);

        popupMenu = new PopupMenu();
        stage.addActor(popupMenu);
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

        // Enter -> Register
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            register();
        }

        // Esc -> Go back
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            goBack();
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
        super.dispose();
        stage.dispose();
    }

    /**
     * Send an HTTP call to the server to register
     * the user.
     */
    public void register() {
        User u = registerMenu.getUserDetails();
        if (new Client().register(u)) {
            game.setUser(u);
            GameSettings gameSettings = new GameSettings.GameSettingsBuilder()
                    .withComputerPlayer(false)
                    .withLevel(0)
                    .withDifficulty(0)
                    .withInfinite(false)
                    .withHelpBox(new TutorialHelpBox(game.batch))
                    .build();
            game.setScreen(new GameScreen(game, gameSettings));
            dispose();
        } else {
            popupMenu.setMessage("It's not possible to register this account.");
        }
    }

    /**
     * Go back to the previous screen.
     */
    public void goBack() {
        game.setScreen(new LoginScreen(game));
        dispose();
    }

}
