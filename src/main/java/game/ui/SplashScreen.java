package game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import game.BubbleSpinner;

public class SplashScreen implements Screen {
    private transient BubbleSpinner game;
    private transient OrthographicCamera camera;
    private transient Texture logo;
    private transient BitmapFont font;

    /**
     * This is the first Screen the game navigates to
     * once started.
     * @param game BubbleSpinner instance.
     */
    public SplashScreen(BubbleSpinner game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        logo = new Texture("assets/logo.png");
        font = new BitmapFont();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(logo,
                Gdx.graphics.getWidth() / 2 - logo.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - logo.getHeight() / 2);
        font.setColor(0, 0, 0, 1);
        font.draw(game.batch, "Press SPACE to start the game.",
                3 * Gdx.graphics.getWidth() / 4,
                Gdx.graphics.getHeight() / 4);
        font.draw(game.batch, "Press ESC to exit the game.",
                Gdx.graphics.getWidth() / 8,
                Gdx.graphics.getHeight() / 4);
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(new LoginScreen(game));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            this.dispose();
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
        logo.dispose();
        game.dispose();
    }
}
