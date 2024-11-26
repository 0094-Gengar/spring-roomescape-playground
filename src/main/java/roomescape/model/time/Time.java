package roomescape.model.time;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Time {

    private Long id;
    private String time;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Time(String time) {
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
