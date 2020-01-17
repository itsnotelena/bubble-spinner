package game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import game.Achievement;
import game.BubbleSpinner;
import game.Pair;

import java.util.List;

public class AchievementScreen extends ScreenAdapter {

    private static String def = "default";
    private transient Label backButton;
    private transient Label badgeLabel;
    private transient Stage stage;
    private transient BubbleSpinner game; // screen to be shown after.
    private transient Achievement achievement;

    /**
     * Constructor for displaying the Achievements.
     * @param game instance of BubbleSpinner.
     */
    public AchievementScreen(BubbleSpinner game) {
        this.game = game;
        this.achievement = new Achievement(game.getUser());
        this.achievement.updateAchievements();
        this.achievement.updateAchievements();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("assets/uiskin.json"));
        backButton = new Label("GO BACK",skin,def);
        backButton.setColor(0.5f,0.5f,1,1);
        backButton.setPosition(Gdx.graphics.getWidth() / 2.f - 100.f,
                0.5f * Gdx.graphics.getHeight() / 8.f);
        backButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MenuScreen(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        badgeLabel = new Label("YOUR BADGES", skin, def);
        badgeLabel.setFontScale(2.f);
        badgeLabel.setColor(1f,0.5f,0.f,1f);
        badgeLabel.setPosition(Gdx.graphics.getWidth() / 2.f - 150.f,
                7 * Gdx.graphics.getHeight() / 8.f);

        stage.addActor(badgeLabel);
        stage.addActor(backButton);

        List<Pair<Image,Label>> imgList = game.getThisUserBadge();

        Table imageTable = new Table();
        Table stringTable = new Table();

        imageTable.defaults().size(100, 100).pad(2.f);
        stringTable.defaults().size(100, 100).pad(2.f);

        for (Pair<Image,Label> pair : imgList) {

            imageTable.add(pair.getLeft()).colspan(2);
            stringTable.add(pair.getRight()).colspan(2);

            imageTable.row();
            stringTable.row();
        }
        imageTable.setPosition(Gdx.graphics.getWidth() / 10.f - 70.f,
                4 * Gdx.graphics.getHeight() / 8.f);
        stringTable.setPosition(Gdx.graphics.getWidth() / 5.f - 70.f,
                4 * Gdx.graphics.getHeight() / 8.f);

        stage.addActor(imageTable);
        stage.addActor(stringTable);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {

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
        stage.dispose();
    }
}
