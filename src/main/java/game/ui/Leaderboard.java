package game.ui;

import client.Client;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import server.Score;
import server.User;

import java.util.List;

public class Leaderboard extends Table {

    private List<User> users;

    public Leaderboard(Skin skin) {
        super(skin);
        this.setBackground("default-window");
        users = new Client().getTop5();

        this.add(new Label("User", skin));
        this.add(new Label("Score a", skin));
        this.add(new Label("Score w", skin));
        this.row();

        for (User s : users) {
            this.add(new Label(s.getUsername(), skin));
            this.add(new Label(String.valueOf(0), skin));
            this.add(new Label(String.valueOf(0), skin));
            this.row();
        }
        this.pack();
    }

}
