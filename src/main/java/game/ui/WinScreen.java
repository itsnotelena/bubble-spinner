package game.ui;

import client.Client;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import game.BubbleSpinner;
import game.GameSettings;
import server.Score;
import server.User;

public class WinScreen implements Screen {

    private transient BubbleSpinner game;
    private transient GameSettings gameSettings;
    private transient PopupMenu popupMenu;
    private transient Stage stage;

    public WinScreen(BubbleSpinner game, GameSettings gameSettings, int score) {
        this.game = game;
        this.gameSettings = gameSettings;
        this.updateScore(score);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        popupMenu = new PopupMenu();
        popupMenu.setMessage("You won the game, Move to the next level!");
        stage.addActor(popupMenu);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

        if (!popupMenu.isVisible()) {
            game.setScreen(new GameScreen(game, gameSettings));
            dispose();
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

    }

    private void updateScore(int score) {
        User user = game.getUser();
        new Client().addScore(new Score(user.getUsername(), score, score));
    }
}
