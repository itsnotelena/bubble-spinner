package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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
        playButton.setWidth(userTextField.getWidth());
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

        table.add(userTextField).size(200, 40);
        table.row();
        table.add(passTextField).size(200, 40);
        table.row();
        table.add().size(200, 40);
        table.row();
        table.add(playButton).size(200, 40);
        stage.addActor(table);
    }

    public void resize(int w, int h) {
        stage.getViewport().update(w,h,true);
    }

    public void render(float delta) {
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }
}
