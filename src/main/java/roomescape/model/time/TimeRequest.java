package roomescape.model.time;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TimeRequest {

    @JsonProperty("time")
    private String time;

    public TimeRequest() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Time toTime() {
        return new Time(this.time);
    }
}
