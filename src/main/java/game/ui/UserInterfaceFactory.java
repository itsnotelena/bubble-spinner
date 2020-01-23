package game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class UserInterfaceFactory {

    private static Skin skin = new Skin(Gdx.files.internal("assets/uiskin.json"));
    private static String DEFAULT = "default";

    public enum LabelColor {
        ButtonText,
        NormalText
    }

    /**
     * Create a TextButton UI element.
     * @param text text.
     * @return TextButton.
     */
    public static TextButton createTextButton(String text) {
        return new TextButton(text, skin, DEFAULT);
    }

    /**
     * Create a TextField UI element.
     * @param text text.
     * @return TextField.
     */
    public static TextField createTextField(String text) {
        TextField textField = new TextField("", skin, DEFAULT);
        textField.setMessageText(text);
        return textField;
    }

    /**
     * Create a Label UI element.
     * @param text text.
     * @return Label.
     */
    public static Label createLabel(String text, LabelColor color) {
        Label label = new Label(text, skin, DEFAULT);
        if (color == LabelColor.ButtonText) {
            label.setColor(0.f,0.f,0.f,1.f);
        } else if (color == LabelColor.NormalText) {
            label.setColor(0.2f,0.2f,0.5f,1);
        }
        return label;
    }
}
