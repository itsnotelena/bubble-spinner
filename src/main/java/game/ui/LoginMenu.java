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

    public LoginMenu(Stage stage, LoginScreen screen) {
        loginScreenLabel = UIFactory.createLabel("Please login below",
                UIFactory.LabelColor.ButtonText);

        userTextField = UIFactory.createTextField("Username");
        passTextField = UIFactory.createTextField("Password");
        passTextField.setPasswordCharacter('*');
        passTextField.setPasswordMode(true);

        playButton = UIFactory.createTextButton("Login");
        register = UIFactory.createLabel("Register", UIFactory.LabelColor.NormalText);
        forgotPass = UIFactory.createLabel("Forgot password", UIFactory.LabelColor.NormalText);

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
