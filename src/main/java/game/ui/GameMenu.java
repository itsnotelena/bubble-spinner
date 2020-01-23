package game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import game.GameSettings;
import game.Observer;
import game.Timer;

public class GameMenu implements Observer {

    private transient SpriteBatch batch;
    private transient GameSettings gameSettings;
    private transient GameScreen gameScreen;
    private transient boolean paused = false;
    private transient Timer timer;
    private transient BitmapFont timerFont;

    public GameMenu(SpriteBatch batch, GameSettings gameSettings, GameScreen gameScreen) {
        this.batch = batch;
        this.gameSettings = gameSettings;
        this.gameScreen = gameScreen;
    }

    @Override
    public void update() {
        if (!gameSettings.isInfinite()) {
            drawTimer();
        }

        if (timer != null && timer.isOver()) {
            gameScreen.dispose();
        }

        if (gameSettings.getHelpBox() != null) {
            gameSettings.getHelpBox().update();
        }
    }

    public void initialize() {
        if (!gameSettings.isInfinite()) {
            addTimer();
        }

        if (gameSettings.isComputerPlayer() && gameSettings.getHelpBox() == null) {
            gameSettings.addHelpBox(new TutorialHelpBox(batch));
        }
    }

    public void addTimer() {
        timer = new Timer();
        timerFont = new BitmapFont();
        timerFont.setColor(Color.BLACK);
        timerFont.getData().setScale(2);
    }

    public void drawTimer() {
        batch.begin();
        timerFont.draw(batch,
                timer.calculateRemainingTime(),
                Gdx.graphics.getHeight() / 8,
                7 * Gdx.graphics.getHeight() / 8
        );
        batch.end();
    }

    public boolean isPaused() {
        return paused;
    }

    /**
     * Switch between pause and resume states.
     */
    public void togglePause() {
        this.paused = !this.paused;

        if (timer == null) {
            return;
        }

        if (this.paused) {
            timer.pause();
        } else {
            timer.resume();
        }
    }
}
