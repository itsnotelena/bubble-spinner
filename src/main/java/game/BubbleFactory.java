package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import config.Config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class BubbleFactory implements Iterator {

    private transient Random rnd;
    private transient Stage stage;
    private transient List<Texture> texture = new ArrayList<>();

    public BubbleFactory(Stage stage) {
        this.stage = stage;
        this.rnd = new Random();
    }

    @Override
    public boolean hasNext() {
        /* TODO
         *
         * Implement a maximum number of balls that can be
         * generated each game, making it harder but
         * still possible to win.
         */
        return true;
    }

    @Override
    public BubbleActor next() {
        return createBubble();
    }

    private BubbleActor createBubble() {
        return new BubbleActor(texture.get(rnd.nextInt(texture.size())), stage);
    }

    public void addTexture(Texture texture) {
        this.texture.add(texture);
    }

    /**
     * Adds the png file to the list of textures.
     * @param text file path
     */
    public void addTexture(String text) {
        addTexture(new Texture(text));
    }

    /**
     * import the colors to the list of textures to be shown
     * to the user.
     *
     * @param max threshold to pick certain colors only
     */
    public void addAllTextures(int max) {
        String[] list = Config.Bubbles.textures;
        for (int i = 0; i < list.length && i <= max; i++) {
            addTexture(list[i]);
        }
    }

}
