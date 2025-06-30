package Main;

import java.awt.*;

public class Time {
    private DayState state;

    private int counter;
    private int hour;

    private boolean hourChanged;

    // 24 in game hours is equivalent to 18 real minutes

    public Time() {
        hour = 11;
        setState(hour);
    }

    public Time(int hour) {
        this.hour = hour;
        setState(hour);
    }

    public boolean hasHourChanged() {
        return hourChanged;
    }

    public void setHourChanged(boolean hourChanged) {
        this.hourChanged = hourChanged;
    }

    public void update() {
        counter ++;
        if (counter == 900) {
            hour ++;
            counter = 0;
            if (hour == 24) {
                hour = 0;
            }
            setState(hour);
            hourChanged = true;
        }

    }

    public DayState getState() {
        return state;
    }

    private void setState(int hour) {
        if (hour >= 21 || hour <= 5) {
            state = DayState.NIGHT;
        }
        else if (hour <= 10) {
            state = DayState.MORNING;
        }
        else if (hour <= 17) {
            state = DayState.DAY;
        }
        else {
            state = DayState.EVENING;
        }
    }

    public int getHour() {
        return hour;
    }


}
