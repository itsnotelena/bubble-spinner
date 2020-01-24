package game.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import server.User;

public class LoginMenu {

    private transient Label loginScreenLabel;
    private transient Label forgotPass;
    private transient Label register;
    private transient TextButton playButton;
    private transient TextField userTextField;
    private transient TextField passTextField;

    /**
     * Constructor.
     * @param stage Stage instance.
     * @param screen LoginScreen instance.
     */
    public LoginMenu(Stage stage, LoginScreen screen) {
        loginScreenLabel = UserInterfaceFactory.createLabel("Please login below",
                UserInterfaceFactory.LabelColor.ButtonText);

        userTextField = UserInterfaceFactory.createTextField("Username");
        passTextField = UserInterfaceFactory.createTextField("Password");
        passTextField.setPasswordCharacter('*');
        passTextField.setPasswordMode(true);

        playButton = UserInterfaceFactory.createTextButton("Login");
        register = UserInterfaceFactory.createLabel("Register",
                UserInterfaceFactory.LabelColor.NormalText);
        forgotPass = UserInterfaceFactory.createLabel("Forgot password",
                UserInterfaceFactory.LabelColor.NormalText);

        playButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                screen.login(getUserDetails());
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });


        register.addListener(new InputListener() {
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
                .addItem(loginScreenLabel)
                .addItem(userTextField)
                .addItem(passTextField)
                .addItem(playButton)
                .addTextItem(register)
                .addTextItem(forgotPass);
    }

    public User getUserDetails() {
        return new User(userTextField.getText(), null, passTextField.getText());
    }
}
