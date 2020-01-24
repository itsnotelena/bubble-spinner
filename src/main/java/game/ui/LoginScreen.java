package game.ui;

import client.Client;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import game.BubbleSpinner;
import server.User;

public class LoginScreen extends ScreenAdapter {

    private transient Stage stage;
    private transient BubbleSpinner game;
    private transient LoginMenu loginMenu;
    private transient PopupMenu popupMenu;

    /**
     * Login Screen.
     * @param game BubbleSpinner instance.
     */
    public LoginScreen(BubbleSpinner game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        loginMenu = new LoginMenu(stage, this);

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

        // Escape -> Go back
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new SplashScreen(game));
        }

        // Enter -> Login
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            login(loginMenu.getUserDetails());
        }

        // R -> Register Screen
        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            register();
        }

        // F -> Forgot Password
        //if (Gdx.input.isKeyPressed(Input.Keys.F)) {
        //}

        // Ctrl + D -> Login with Default user
        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)
            && Gdx.input.isKeyPressed(Input.Keys.D)) {
            User defaultUser = new User("", "", "");
            new Client().register(defaultUser);
            login(defaultUser);
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
     * Go the register screen.
     */
    public void register() {
        game.setScreen(new RegisterScreen(game));
        dispose();
    }

    /**
     * Send an HTTP call to the server to verify the user.
     */
    public void login(User user) {
        if (new Client().authenticate(user)) {
            game.setUser(user);
            game.setScreen(new MenuScreen(game));
            dispose();
        } else {
            popupMenu.setMessage("Invalid credentials.");
        }
    }
}
