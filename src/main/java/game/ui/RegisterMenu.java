package game.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import server.User;

public class RegisterMenu {

    private transient Label registerScreenLabel;
    private transient TextField userTextField;
    private transient TextField passTextField;
    private transient TextField emailTextField;
    private transient TextButton registerButton;
    private transient Label goBackField;

    /**
     * Constructor.
     * @param stage Stage instance.
     * @param screen RegisterScreen instance.
     */
    public RegisterMenu(Stage stage, RegisterScreen screen) {
        registerScreenLabel = UserInterfaceFactory.createLabel("Please register below",
                UserInterfaceFactory.LabelColor.ButtonText);

        emailTextField = UserInterfaceFactory.createTextField("Email");
        userTextField = UserInterfaceFactory.createTextField("Username");
        passTextField = UserInterfaceFactory.createTextField("Password");
        passTextField.setPasswordCharacter('*');
        passTextField.setPasswordMode(true);

        registerButton = UserInterfaceFactory.createTextButton("Register");
        goBackField = UserInterfaceFactory.createLabel("Go back",
                UserInterfaceFactory.LabelColor.NormalText);

        goBackField.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                screen.goBack();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });


        registerButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                screen.register();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        new TableMenu(stage)
                .addItem(registerScreenLabel)
                .addItem(emailTextField)
                .addItem(userTextField)
                .addItem(passTextField)
                .addItem(registerButton)
                .addTextItem(goBackField);
    }

    public User getUserDetails() {
        return new User(userTextField.getText(),
                emailTextField.getText(), passTextField.getText());
    }
}
