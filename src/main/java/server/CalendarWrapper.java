package server;

import java.util.Calendar;
import java.util.Date;

public class CalendarWrapper {

    private transient Calendar calendar;

    public CalendarWrapper() {
        calendar = Calendar.getInstance();
    }

    public void setTime(Date date) {
        calendar.setTime(date);
    }

    public int get(int day) {
        return calendar.get(day);
    }
}
