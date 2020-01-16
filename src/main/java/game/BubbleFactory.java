package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import config.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BubbleFactory implements AbstractBubbleFactory {

    private transient Random rnd;
    private transient Stage stage;
    private transient List<Texture> texture = new ArrayList<>();

    public BubbleFactory(Stage stage) {
        this.stage = stage;
        this.rnd = new Random();
    }

    @Override
    public BubbleActor createBubble() {
        int colorId = rnd.nextInt(texture.size());
        BubbleActor actor = new BubbleActor(texture.get(colorId), stage);
        actor.setColorId(colorId);
        return actor;
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
