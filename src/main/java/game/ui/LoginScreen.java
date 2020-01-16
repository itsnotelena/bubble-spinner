package game.ui;

import client.Client;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import game.BubbleSpinner;
import server.User;


public class LoginScreen extends ScreenAdapter {

    private transient Stage stage;
    private transient BubbleSpinner game;
    private transient Table table;
    private transient Label loginScreenLabel;
    private transient TextField userTextField;
    private transient TextField passTextField;
    private transient TextButton playButton;
    private transient Label register;
    private transient Label forgotPass;
    private transient PopupMenu popupMenu;
    static final String def = "default";

    /**
     * Login Screen.
     * @param game BubbleSpinner instance.
     */

    public LoginScreen(BubbleSpinner game) {
        this.game = game;
    }

    @Override
    public void show() {
        Skin skin = new Skin(Gdx.files.internal("assets/uiskin.json"));

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        loginScreenLabel = new Label("Please login below", skin, def);
        loginScreenLabel.setColor(0.f,0.f,0.f,1.f);

        userTextField = new TextField("", skin, def);
        userTextField.setMessageText("Username");

        passTextField = new TextField("", skin, def);
        passTextField.setMessageText("Password");
        passTextField.setPasswordCharacter('*');
        passTextField.setPasswordMode(true);

        playButton = new TextButton("Login", skin, def);
        register = new Label("Register", skin, def);
        register.setColor(new Color(0.2f,0.2f,0.5f,1));
        forgotPass = new Label("Forgot password", skin, def);
        forgotPass.setColor(new Color(0.2f,0.2f,0.5f,1));

        playButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                login(new User(userTextField.getText(), null, passTextField.getText()));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });


        register.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new RegisterScreen(game));
                dispose();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        int w = 300;
        int padding = 5;
        int h = 50;

        table = new Table();
        table.setFillParent(true);
        table.defaults().size(w, h).pad(padding);
        table.setColor(new Color(0,0,0,1));
        table.add(loginScreenLabel).colspan(2);
        table.row();
        table.add(userTextField).colspan(2);
        table.row();
        table.add(passTextField).colspan(2);
        table.row();
        table.row();
        table.add(playButton).colspan(2);
        table.row();
        table.add(register).colspan(1).width(w / 2);
        table.add(forgotPass).colspan(1).width(w / 2);
        stage.addActor(table);

        popupMenu = new PopupMenu(skin);
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

        // Enter -> Login
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            login(new User(userTextField.getText(), null, passTextField.getText()));
        }

        // R -> Register Screen
        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            game.setScreen(new RegisterScreen(game));
            dispose();
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
     * Send an HTTP call to the server to verify the user.
     */
    private void login(User user) {
        if (new Client().authenticate(user)) {
            game.setUser(user);
            game.setScreen(new MenuScreen(game));
            dispose();
        } else {
            popupMenu.setMessage("Invalid credentials.");
        }
    }
}
