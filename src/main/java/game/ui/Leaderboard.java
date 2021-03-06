package game.ui;

import client.Client;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import java.util.List;
import server.Score;

public class Leaderboard extends Table {

    private transient Skin skin = new Skin(Gdx.files.internal("assets/uiskin.json"));
    private transient List<Score> scores;

    /**
     * Contructor for a leaderboard scene2D element.
     */
    public Leaderboard() {
        super(new Skin(Gdx.files.internal("assets/uiskin.json")));
        this.setBackground("default-window");
        scores = new Client().getTop5();

        this.add(new Label("User", skin));
        this.add(new Label("Score", skin));
        this.add(new Label("Weekly", skin));
        this.row();

        for (Score s : scores) {
            this.add(new Label(s.getUsername(), skin));
            this.add(new Label(String.valueOf(s.getScoreA()), skin));
            this.add(new Label(String.valueOf(s.getHighestWeekScore()), skin));
            this.row();
        }

        this.pack();
    }

}
