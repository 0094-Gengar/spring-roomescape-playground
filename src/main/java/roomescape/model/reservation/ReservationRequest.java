package roomescape.model.reservation;

import roomescape.model.time.Time;

public class ReservationRequest {
    private String name;
    private String date;
    private Time time;

    public ReservationRequest(String name, String date, Time time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }
}
