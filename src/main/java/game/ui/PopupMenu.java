package game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class PopupMenu extends Table {

    private static int width = 300;
    private static int height = 30;
    private static String def = "default";
    private transient Label gamePausedLabel;

    /**
     * PopMenu constructor.
     */
    public PopupMenu() {
        super();

        Skin skin = new Skin(Gdx.files.internal("assets/uiskin.json"));

        gamePausedLabel = new Label("", skin, def);
        gamePausedLabel.setColor(0.f,0.f,0.f,1.f);
        TextButton okButton = new TextButton("Ok", skin, def);
        okButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                setVisible(false);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        defaults().size(width, height).pad(5);
        add(gamePausedLabel);
        row();
        add(okButton);
        setBounds((Gdx.graphics.getWidth() - width) / 2,
                (Gdx.graphics.getHeight() - height) / 8, width, height); // Screen center.
        setVisible(false);
    }

    public void setMessage(String message) {
        setVisible(true);
        gamePausedLabel.setText(message);
    }

}
