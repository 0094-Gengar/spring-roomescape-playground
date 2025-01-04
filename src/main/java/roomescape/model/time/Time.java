package roomescape.model.time;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Time { // 도메인-비즈니스에 관련이 있는 경우 이름을 time 으로 해도 좋지만, 데이터베이스와 접근하기 때문에 ReservationTime 으로 고려하기

    private Long id;
    private String time;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Time(@JsonProperty("time") String time) {
        this.time = time;
    }

    public Time(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
