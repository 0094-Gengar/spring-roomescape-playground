package roomescape.model.reservation;

import roomescape.model.time.Time;

// 예약 관련 데이터베이스 엔티티
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

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
