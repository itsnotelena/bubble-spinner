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
import game.GameSettings;
import server.User;



public class RegisterScreen extends ScreenAdapter {

    private transient Stage stage;
    private transient BubbleSpinner game;
    private transient Table table;
    private transient Label registerScreenLabel;
    private transient TextField userTextField;
    private transient TextField passTextField;
    private transient TextField emailTextField;
    private transient TextButton registerButton;
    private transient Label goBackField;
    static final String def = "default";
    private transient PopupMenu popupMenu;


    /**
     * Registerss Screen.
     * @param game BubbleSpinner instance.
     */
    public RegisterScreen(BubbleSpinner game) {
        this.game = game;
    }

    @Override
    public void show() {
        Skin skin = new Skin(Gdx.files.internal("assets/uiskin.json"));

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        registerScreenLabel = new Label("Please register below", skin, def);
        registerScreenLabel.setColor(0.f,0.f,0.f,1.f);

        emailTextField = new TextField("", skin, def);
        emailTextField.setMessageText("Email");

        userTextField = new TextField("", skin, def);
        userTextField.setMessageText("Username");

        passTextField = new TextField("", skin, def);
        passTextField.setMessageText("Password");
        passTextField.setPasswordCharacter('*');
        passTextField.setPasswordMode(true);

        registerButton = new TextButton("Register", skin, def);

        goBackField = new Label("Go back", skin, def);
        goBackField.setColor(new Color(0.2f,0.2f,0.5f,1));

        goBackField.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen( new LoginScreen(game));
                dispose();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });


        registerButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                register();
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
        table.add(registerScreenLabel).colspan(2);
        table.row();
        table.add(emailTextField).colspan(2);
        table.row();
        table.add(userTextField).colspan(2);
        table.row();
        table.add(passTextField).colspan(2);
        table.row();
        table.row();
        table.add(registerButton).colspan(2);
        table.row();
        table.add(goBackField).colspan(1).width(w / 2);
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

        // Enter -> Register
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            register();
        }

        // Esc -> Go back
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen( new LoginScreen(game));
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
        super.dispose();
        stage.dispose();
    }

    /**
     * Send an HTTP call to the server to register
     * the user.
     */
    private void register() {
        User u = new User(userTextField.getText(),
                emailTextField.getText(), passTextField.getText());
        if (new Client().register(u)) {
            game.setUser(u);
            GameSettings gameSettings = new GameSettings.GameSettingsBuilder()
                    .withComputerPlayer(false)
                    .withLevel(0)
                    .withDifficulty(0)
                    .withInfinite(false)
                    .build();
            game.setScreen(new GameScreen(game, gameSettings));
            dispose();
        } else {
            popupMenu.setMessage("It's not possible to register this account.");
        }
    }

}
