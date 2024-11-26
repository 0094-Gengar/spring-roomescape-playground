package roomescape.model.reservation;

import roomescape.model.time.Time;

public class Reservation {

    private Long id;
    private String name;
    private String date;
    private Time time;

    public Reservation(String name, String date, Time time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
