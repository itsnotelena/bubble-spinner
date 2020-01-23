package game.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class TableMenu extends Table {

    private static final int WIDTH = 300;
    private static final int HEIGHT = 50;
    private static final int PADDING = 5;
    private static final int COLSPAN = 2;

    public TableMenu(Stage stage) {
        super();
        setFillParent(true);
        defaults().size(WIDTH, HEIGHT).pad(PADDING);
        stage.addActor(this);
    }

    public TableMenu addItem(Actor item) {
        add(item).colspan(COLSPAN);
        row();
        return this;
    }

    public TableMenu addTextItem(Actor item) {
        add(item).colspan(1).width(WIDTH / 2);
        return this;
    }
}
