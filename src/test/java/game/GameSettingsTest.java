package game;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameSettingsTest {

    @Test
    public void testGameSettingsBuilder() {
        GameSettings gameSettings = new GameSettings.GameSettingsBuilder()
                        .withComputerPlayer(true)
                        .withDifficulty(0)
                        .withInfinite(false)
                        .withLevel(10)
                        .withHelpBox(null)
                        .build();
        gameSettings.incrementLevel();
        Assertions.assertThat(gameSettings.getDifficulty()).isEqualTo(0);
        Assertions.assertThat(gameSettings.getLevel()).isEqualTo(11);
        Assertions.assertThat(gameSettings.isComputerPlayer()).isTrue();
        Assertions.assertThat(gameSettings.isInfinite()).isFalse();
        Assertions.assertThat(gameSettings.getHelpBox()).isNull();
    }
}
