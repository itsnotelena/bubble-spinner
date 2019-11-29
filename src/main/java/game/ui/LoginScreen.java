package game.ui;


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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import game.BubbleSpinner;

public class LoginScreen extends ScreenAdapter {

    private transient Stage stage;
    private transient BubbleSpinner game;
    private transient Table table;
    private transient TextField userTextField;
    private transient TextField passTextField;
    private transient TextButton playButton;
    private transient Label register;
    private transient Label forgotPass;
    static final String def = "default";


    /**
     * Login Screen.
     * @param game BubbleSpinner instance.
     */

    public LoginScreen(BubbleSpinner game) {
        this.game = game;
    }

    public void show() {
        Skin skin = new Skin(Gdx.files.internal("assets/uiskin.json"));

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        userTextField = new TextField("Username", skin, def);
        passTextField = new TextField("Password", skin, def);

        playButton = new TextButton("Login", skin, def);
        register = new Label("Register", skin, def);
        register.setColor(new Color(0.5f,0.5f,0.5f,1));
        forgotPass = new Label("Forgot password", skin, def);
        forgotPass.setColor(new Color(0.5f,0.5f,0.5f,1));
        playButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
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
        table.add(userTextField).colspan(2);
        table.row();
        table.add(passTextField).colspan(2);
        table.row();
        table.row();
        table.add(playButton).colspan(2);
        table.row();
        table.add(register).colspan(1).width(w/2);
        table.add(forgotPass).colspan(1).width(w/2);
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
