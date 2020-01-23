package game.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import config.Config;
import game.GameSettings;

public class MainMenu {

    private transient TextButton startButton;
    private transient TextButton computerButton;
    private transient TextButton timerButton;
    private transient TextButton difficultyButton;
    private transient TextButton exitButton;
    private transient TextButton logoutButton;
    private transient TextButton achievementButton;
    private transient boolean computerPlayer;
    private transient int difficulty;

    /**
     * Constructor.
     * @param stage Stage instance.
     * @param screen MenuScreen instance.
     */
    public MainMenu(Stage stage, MenuScreen screen) {
        startButton = UserInterfaceFactory.createTextButton("Start game");
        difficultyButton = UserInterfaceFactory.createTextButton("Difficulty: Easy");
        logoutButton = UserInterfaceFactory.createTextButton("Logout");
        achievementButton = UserInterfaceFactory.createTextButton("AchievementScreen");
        exitButton = UserInterfaceFactory.createTextButton("Exit");

        computerButton = UserInterfaceFactory.createTextButton("Computer Player OFF");
        timerButton = UserInterfaceFactory.createTextButton("Timer: 10 minutes");

        Config.Game.GAME_TIME = Config.Time.DEFAULT;
        computerPlayer = false;

        startButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                screen.startGame(getGameSettings());
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        computerButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return switchComputerPlayer();
            }
        });

        timerButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                incrementTimer();
                return true;
            }
        });

        difficultyButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                changeDifficulty();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });


        logoutButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                screen.logout();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        exitButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                screen.dispose();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        achievementButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                screen.achievementPanel();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        new TableMenu(stage)
                .addItem(startButton)
                .addItem(computerButton)
                .addItem(timerButton)
                .addItem(achievementButton)
                .addItem(difficultyButton)
                .addItem(logoutButton)
                .addItem(exitButton);
    }

    /**
     * Get the current gameSettings of the game.
     * @return GameSettings instance.
     */
    public GameSettings getGameSettings() {
        return new GameSettings.GameSettingsBuilder()
                .withComputerPlayer(computerPlayer)
                .withLevel(0)
                .withDifficulty(difficulty)
                .withInfinite(Config.Game.GAME_TIME == 0)
                .build();
    }

    /**
     * Switch between computer and normal player.
     * @return computerPlayer boolean.
     */
    public boolean switchComputerPlayer() {
        computerPlayer = !computerPlayer;
        computerButton.setText("Computer Player " + (computerPlayer ? "ON" : "OFF"));
        return computerPlayer;
    }

    /**
     * Increment the timer by 10 minutes.
     */
    public void incrementTimer() {
        Config.Game.GAME_TIME = (Config.Game.GAME_TIME + Config.Time.DEFAULT) % Config.Time.HOUR;
        String time = Config.Game.GAME_TIME == 0
                ? "infinite" :
                Integer.toString(Config.Game.GAME_TIME / Config.Time.MINUTE);
        timerButton.setText("Timer: " + time + " minutes");
    }

    /**
     * Change the difficulty of the game.
     */
    public void changeDifficulty() {
        difficulty = (difficulty + 1) % Config.Difficulty.types.length;
        difficultyButton.setText("Diffulty: " + Config.Difficulty.types[difficulty]);
    }
}
