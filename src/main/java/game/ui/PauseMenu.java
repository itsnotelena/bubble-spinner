package game.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PauseMenu extends Stage {
    private transient GameScreen game;
    private transient int width = 300;
    private transient int height = 50;

    public PauseMenu(GameScreen game, Skin skin) {
        super(new ScreenViewport());
        this.game = game;
        Gdx.input.setInputProcessor(this);

        String def = "default";
        // Some text tot tell the user whats going on
        Label gamePausedLabel = new Label("Game Paused", skin, def);
        gamePausedLabel.setColor(0.f,0.f,0.f,1.f);

        // Setup the first button, a resume button
        TextButton resumeButton = new TextButton("Resume", skin, def);
        resumeButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.togglePause();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        // Setup a second button, one to access options
        TextButton optionsButton = new TextButton("Options", skin, def);
        optionsButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        // Setup a third button, one to exit the current game
        TextButton exitButton = new TextButton("Exit", skin, def);
        exitButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.dispose();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        Table table = new Table();
        table.defaults().size(width, height).pad(5);

        table.add(gamePausedLabel);
        table.row();
        table.add(resumeButton);
        table.row();
        table.add(optionsButton);
        table.row();
        table.add(exitButton);
        table.row();
        this.addActor(table);

        float newWidth = width, newHeight = height * 5;
        table.setBounds((Gdx.graphics.getWidth() - newWidth ) / 2,
              (Gdx.graphics.getHeight() - newHeight ) / 2, newWidth , newHeight ); // Center on screen.
    }

}
