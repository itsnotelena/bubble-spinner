package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import config.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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

    public void addTexture(String text) {
        addTexture(new Texture(text));
    }

    public void addAllTextures() {
        for(String name : Config.Bubbles.textures) {
                addTexture(name);
        }
    }

}
