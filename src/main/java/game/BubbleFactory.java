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
    private transient int maximumBubbles;

    public BubbleFactory(Stage stage, int difficulty) {
        this.stage = stage;
        this.rnd = new Random();
        this.maximumBubbles = 5 + difficulty * 2;
    }

    public BubbleFactory(Stage stage) {
        this(stage, 0);
    }

    @Override
    public BubbleActor createBubble() {
        int colorId = rnd.nextInt(texture.size());
        BubbleActor actor = new BubbleActor(texture.get(colorId), stage);
        actor.setColorId(colorId);
        return actor;
    }

    /**
     * Creates a bubble given a hash map of bubbles already
     * present in the hexagon.
     * @param mapBubbles is a hash map of the Hexagon.
     * @return a new BubbleActor.
     */
    public BubbleActor createBubbleGivenMap(int[] mapBubbles) {
        List<Integer> possibleColors = new ArrayList<>();
        for (int i = 0; i < mapBubbles.length; ++i) {
            if (mapBubbles[i] != 0) {
                possibleColors.add(i);
            }
        }
        if (possibleColors.size() == 0) {
            return null;
        }

        int pos = rnd.nextInt(possibleColors.size());
        BubbleActor actor = new BubbleActor(texture.get(possibleColors.get(pos)), stage);
        actor.setColorId(possibleColors.get(pos));
        return actor;
    }

    public BubbleActor createCenterBubble() {
        Texture centerTexture = new Texture("assets/Bubbles/Black.png");
        BubbleActor actor = new BubbleActor(centerTexture, stage);
        actor.setColorId(-1);
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
     */
    public void addAllTextures() {
        String[] list = Config.Bubbles.textures;
        for (int i = 0; i < list.length && i < maximumBubbles; i++) {
            addTexture(list[i]);
        }
    }

}
