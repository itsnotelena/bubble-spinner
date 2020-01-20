package game;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import server.User;

public class BubbleSpinnerTest {

    @Test
    public void testSetUser() {
        BubbleSpinner game = new BubbleSpinner();
        User user = new User("", "", "");
        game.setUser(user);
        Assertions.assertThat(game.getUser()).isEqualTo(user);
    }
}
