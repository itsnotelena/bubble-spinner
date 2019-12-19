package game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class PauseMenu extends Window {

    private transient int width = 300;
    private transient int height = 50;

    public PauseMenu(Skin skin) {
        super("Game paused", skin);

        // Setup the first button, a resume button
        TextButton resumeButton = new TextButton("Resume", skin);
        resumeButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        // Setup a second button, one to access options
        TextButton optionsButton = new TextButton("Options", skin);
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
        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        Table table = new Table();
        table.defaults().size(width, height).pad(5);
        table.add(resumeButton);
        table.row();
        table.add(optionsButton);
        table.row();
        table.add(exitButton);
        table.row();
        this.add(table);
        this.pack(); //Important! Correctly scales the window after adding new elements.
        float newWidth = width, newHeight = height * 5;
        this.setBounds((Gdx.graphics.getWidth() - newWidth ) / 2,
                (Gdx.graphics.getHeight() - newHeight ) / 2, newWidth , newHeight ); //Center on screen.
    }
}
