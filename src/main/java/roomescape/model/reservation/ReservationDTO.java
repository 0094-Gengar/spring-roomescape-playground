package roomescape.model.reservation;

public class ReservationDTO {
    private String name;
    private String date;
    private Long time;

    public ReservationDTO(String name, String date, Long time) {
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

    public Long getTime() {
        return time;
    }
}
