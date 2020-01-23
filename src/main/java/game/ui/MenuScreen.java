package game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import config.Config;
import config.Config.Game;
import config.Config.Time;
import game.BubbleSpinner;
import game.GameSettings;


public class MenuScreen extends ScreenAdapter {

    private transient Stage stage;
    private transient BubbleSpinner game;
    private transient Table table;
    private transient TextButton startButton;
    private transient TextButton computerButton;
    private transient TextButton timerButton;
    private transient TextButton difficultyButton;
    private transient TextButton exitButton;
    private transient boolean computerPlayer;
    private transient TextButton logoutButton;
    private transient TextButton loggedIn;
    private transient TextButton achievementButton;
    private transient Leaderboard leaderboard;
    private transient GameSettings gameSettings;
    private transient int difficulty = 0;

    /**
     * Login Screen.
     * @param game BubbleSpinner instance.
     */

    public MenuScreen(BubbleSpinner game) {
        this.game = game;
    }

    @Override
    public void show() {
        Skin skin = new Skin(Gdx.files.internal("assets/uiskin.json"));

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        String def = "default";
        startButton = new TextButton("Start game", skin, def);
        difficultyButton = new TextButton("Difficulty: Easy", skin, def);
        logoutButton = new TextButton("Logout", skin, def);
        achievementButton = new TextButton("AchievementScreen",skin,def);
        exitButton = new TextButton("Exit", skin, def);

        computerButton = new TextButton("Computer Player OFF", skin, def);
        computerPlayer = false;
        timerButton = new TextButton("Timer: 10 minutes", skin, def);
        Game.GAME_TIME = Time.DEFAULT;
        
        startButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                startGame();
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
                logout();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        exitButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        achievementButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                achievementPanel();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        int w = 300;
        int padding = 5;
        int h = 50;

        table = new Table();
        table.setFillParent(true);
        table.defaults().size(w, h).pad(padding);

        table.add(startButton).colspan(2);
        table.row();
        table.add(computerButton).colspan(2);
        table.row();
        table.add(timerButton).colspan(2);
        table.row();
        table.add(achievementButton).colspan(2);
        table.row();
        table.add(difficultyButton).colspan(2);
        table.row();
        table.add(logoutButton).colspan(2);
        table.row();
        loggedIn = new TextButton("Player : " + game.getUser().getUsername(), skin, def);
        loggedIn.setPosition(Gdx.graphics.getWidth() / 8.f,
                7 * Gdx.graphics.getHeight() / 8.f);
        stage.addActor(loggedIn);

        table.add(exitButton).colspan(2);
        stage.addActor(table);

        leaderboard = new Leaderboard(skin);
        leaderboard.setPosition(
                Gdx.graphics.getWidth() * 6 / 8.f,
                4 * Gdx.graphics.getHeight() / 8.f
        );
        stage.addActor(leaderboard);
    }

    @Override
    public void resize(int w, int h) {
        stage.getViewport().update(w,h,true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

        // Space -> Start Game
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            startGame();
        }

        // C -> Computer Player
        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            switchComputerPlayer();
        }

        // T -> Timer
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            incrementTimer();
        }

        // D -> Difficulty
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            changeDifficulty();
        }

        // Backspace -> Logout
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
            logout();
        }

        // Escape -> Exit
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            dispose();
        }
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
        game.dispose();
    }

    private void startGame() {
        GameSettings.GameSettingsBuilder builder =  new GameSettings.GameSettingsBuilder()
                .withComputerPlayer(computerPlayer)
                .withLevel(0)
                .withDifficulty(difficulty)
                .withInfinite(Game.GAME_TIME == 0);

        gameSettings = builder.build();

        if(computerPlayer) {
            gameSettings = builder.withHelpBox(new TutorialHelpBox(game.batch)).build();
        }


        game.setScreen(new GameScreen(game, gameSettings));
        stage.dispose();
    }

    private void achievementPanel() {
        game.setScreen(new AchievementScreen(game));
        stage.dispose();
    }

    private boolean switchComputerPlayer() {
        computerPlayer = !computerPlayer;
        computerButton.setText("Computer Player " + (computerPlayer ? "ON" : "OFF"));
        return computerPlayer;
    }

    private void incrementTimer() {
        Game.GAME_TIME = (Game.GAME_TIME + Time.DEFAULT) % Time.HOUR;
        String time = Game.GAME_TIME == 0
                ? "infinite" :
                Integer.toString(Game.GAME_TIME / Time.MINUTE);
        timerButton.setText("Timer: " + time + " minutes");
    }

    private void changeDifficulty() {
        difficulty = (difficulty + 1) % Config.Difficulty.types.length;
        difficultyButton.setText("Diffulty: " + Config.Difficulty.types[difficulty]);
    }

    private void logout() {
        game.setScreen(new LoginScreen(game));
        stage.dispose();
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public void setGameSettings(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }
}
