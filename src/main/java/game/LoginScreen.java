package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LoginScreen extends ScreenAdapter {

    private transient Stage stage;
    private transient Game game;

    public LoginScreen(Game game) {
        this.game = game;
    }

    public void show() {
        Skin skin = new Skin(Gdx.files.internal("assets/uiskin.json"));

        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);
        Label title = new Label("Hello World", skin,"default");
        title.setAlignment(Align.center);
        title.setY((float) (Gdx.graphics.getHeight() * 2 / 3.));
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);

        TextButton playButton = new TextButton("Play!", skin);
        playButton.setWidth((float) (Gdx.graphics.getWidth() / 2.));
        playButton.setPosition(
                (float) (Gdx.graphics.getWidth() / 2. - playButton.getWidth() / 2),
                (float) (Gdx.graphics.getHeight() / 2. - playButton.getHeight() / 2));
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
        stage.addActor(playButton);
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
