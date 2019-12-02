package game.ui;

import client.Client;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import game.BubbleSpinner;
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
    static final String def = "default";


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

        registerButton = new TextButton("Register", skin, def);

        registerButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                User u = new User(userTextField.getText(), emailTextField.getText(), passTextField.getText());
                if (new Client().register(u)) {
                    game.setUser(u);
                    game.setScreen(new GameScreen(game));
                    dispose();
                }
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
        stage.addActor(table);
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
}
