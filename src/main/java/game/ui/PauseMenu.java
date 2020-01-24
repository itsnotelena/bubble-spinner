package game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import game.BubbleSpinner;

public class PauseMenu extends Stage {

    private transient Skin skin = new Skin(Gdx.files.internal("assets/uiskin.json"));

    private transient int width = 300;
    private transient int height = 50;

    private transient BubbleSpinner game;
    private transient GameMenu gameMenu;

    /**
     * Pause menu.
     * @param game BubbleSpinner instance.
     * @param gameMenu GameMenu instance.
     */
    public PauseMenu(BubbleSpinner game, GameMenu gameMenu) {
        super(new ScreenViewport());
        this.game = game;
        this.gameMenu = gameMenu;
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
                gameMenu.togglePause();
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

        // Setup a third button, one to exit the current gameScreen
        TextButton exitButton = new TextButton("Exit", skin, def);
        exitButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MenuScreen(game));
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

        float newWidth = width;
        float newHeight = height * 5;
        table.setBounds((Gdx.graphics.getWidth() - newWidth) / 2,
              (Gdx.graphics.getHeight() - newHeight) / 2, newWidth, newHeight); // Screen center.
    }

    @Override
    public void act() {
        super.act();

        // Space -> Resume Game
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            gameMenu.togglePause();
        }

        // Escape -> Exit Game
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            game.setScreen(new MenuScreen(game));
        }
    }
}
