package server;

import config.Config;
import java.util.Calendar;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.Test;
import org.mockito.Mockito;


public class CheckTimeTest {

    transient boolean res = Config.Time.NeedToBeRestarted;

    @AfterEach
    void clean() {
        Config.Time.NeedToBeRestarted = res;
    }

    @Test
    public void testTimeItsMonday() {
        CalendarWrapper date = Mockito.mock(CalendarWrapper.class);
        Mockito.doNothing().when(date).setTime(Mockito.any());
        Mockito.when(date.get(Calendar.DAY_OF_WEEK)).thenReturn(Calendar.MONDAY);
        DbImplement dbImplement = new DbImplement(new DbAdapter("test"));
        dbImplement.setCalendar(date);
        Assertions.assertThat(dbImplement.checkTimeToChange()).isTrue();
    }

    @Test
    public void testTimeItsMondayAndDoesntNeedToBeChanged() {
        CalendarWrapper date = Mockito.mock(CalendarWrapper.class);
        Mockito.doNothing().when(date).setTime(Mockito.any());
        Mockito.when(date.get(Calendar.DAY_OF_WEEK)).thenReturn(Calendar.MONDAY);
        DbImplement dbImplement = new DbImplement(new DbAdapter("test"));
        dbImplement.setCalendar(date);
        Config.Time.NeedToBeRestarted = true;
        Assertions.assertThat(dbImplement.checkTimeToChange()).isFalse();
    }

    @Test
    public void testTimeItsNotMonday() {
        CalendarWrapper date = Mockito.mock(CalendarWrapper.class);
        Mockito.doNothing().when(date).setTime(Mockito.any());
        Mockito.when(date.get(Calendar.DAY_OF_WEEK)).thenReturn(Calendar.SUNDAY);
        DbImplement dbImplement = new DbImplement(new DbAdapter("test"));
        dbImplement.setCalendar(date);
        Assertions.assertThat(dbImplement.checkTimeToChange()).isFalse();
    }
}
