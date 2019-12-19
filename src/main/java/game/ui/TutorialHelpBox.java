package game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import game.Observer;

import java.util.Stack;

public class TutorialHelpBox implements Observer {

    private transient SpriteBatch batch;
    private transient ShapeRenderer transparentBox;
    private transient boolean isEnabled;

    private transient Stack<String> forward;
    private transient Stack<String> back;
    private transient BitmapFont text;


    /**
     * Constructor for the Tutorial help box.
     * @param batch is the SpriteBatch where the box will be drawn.
     */
    public TutorialHelpBox(SpriteBatch batch) {
        this.batch = batch;
        this.transparentBox = new ShapeRenderer();
        this.isEnabled = true;

        forward = new Stack<>();
        back = new Stack<>();

        forward.push("Third text");
        forward.push("Second text");
        forward.push("First text");
        text = new BitmapFont();
    }

    @Override
    public void update() {
        if (!isEnabled) {
            return;
        }

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.begin();
        transparentBox.setProjectionMatrix(batch.getProjectionMatrix());
        transparentBox.begin(ShapeRenderer.ShapeType.Filled);
        transparentBox.setColor(0.82f, 0.82f, 0.82f,0.15f);
        transparentBox.rect(32, 32, 400, 250);
        transparentBox.end();
        batch.end();

        batch.begin();
        text.setColor(Color.BLACK);
        text.draw(batch, forward.isEmpty() ? "" : forward.peek(), 64, 218);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            right();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            left();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            close();
        }
    }

    /**
     * Close the help box.
     */
    private void close() {
        isEnabled = false;
    }

    /**
     * Go to the right message in the help box.
     */
    @SuppressWarnings(value = "PMD.AvoidLiteralsInIfCondition")
    // 1 is not a magic number but makes sure that there is always
    // at least 1 element in the stack.
    private void right() {
        if (forward.size() > 1) {
            back.push(forward.pop());
        }
    }

    /**
     * Go left one message in the help box.
     */
    private void left() {
        if (!back.isEmpty()) {
            forward.push(back.pop());
        }
    }

}
