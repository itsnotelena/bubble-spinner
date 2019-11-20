package game;

import com.badlogic.gdx.Game;
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

public class LoginScreen extends ScreenAdapter {

    private transient Stage stage;
    private transient Game game;
    private transient Table table;

    public LoginScreen(Game game) {
        this.game = game;
    }

    public void show() {
        Skin skin = new Skin(Gdx.files.internal("assets/uiskin.json"));

        stage = new Stage(new ScreenViewport());
        table = new Table();
        table.setFillParent(true);

        Gdx.input.setInputProcessor(stage);
        TextField userTextField = new TextField("Username", skin,"default");
        TextField passTextField = new TextField("Password", skin, "default");

        TextButton playButton = new TextButton("Play!", skin, "default");
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

        table.defaults().size(300,40).pad(5);
        table.setColor(new Color(0,0,0,1));
        table.add(userTextField);
        table.row();
        table.add(passTextField);
        table.row();
        table.row();
        table.add(playButton);
        stage.addActor(table);
    }

    public void resize(int w, int h) {
        stage.getViewport().update(w,h,true);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }
}
