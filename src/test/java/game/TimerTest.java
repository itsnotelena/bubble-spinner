package game;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class TimerTest {

    @Test
    public void testCalculateTime() {
        Timer t = new Timer();
        t.resume();
        Assertions.assertThat(t.calculateRemainingTime()).isEqualTo("10:00");
    }

    @Test
    public void testPauseAndResumeTimer() {
        Timer t = new Timer();
        t.pause();
        Assertions.assertThat(t.calculateRemainingTime()).isEqualTo("10:00");
        t.resume();
        try {
            Thread.sleep(1250);
        } catch (InterruptedException ignored) {
            assert true;
        }
        Assertions.assertThat(t.calculateRemainingTime()).isEqualTo("09:59");
    }

    @Test
    public void testIsOver() {
        long mockTime = System.currentTimeMillis();
        Timer t = new Timer();
        t.setStartingTime(mockTime);
        Assertions.assertThat(t.isOver()).isFalse();
        t.setStartingTime(mockTime - 600000);
        Assertions.assertThat(t.isOver()).isTrue();
    }
}
