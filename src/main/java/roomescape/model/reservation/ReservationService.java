package roomescape.model.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import roomescape.exception.InvalidReservationParameterException;
import roomescape.exception.NotFoundReservationException;
import roomescape.model.time.Time;

import java.sql.PreparedStatement;
import java.util.List;

@Service
public class ReservationService {
    private final JdbcTemplate jdbcTemplate;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(JdbcTemplate jdbcTemplate, ReservationRepository reservationRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation addReservation(ReservationRequest reservationRequest) {
        if (reservationRequest.getName() == null || reservationRequest.getName().isEmpty() ||
                reservationRequest.getDate() == null || reservationRequest.getDate().isEmpty() ||
                reservationRequest.getTime() == null) {
            throw new InvalidReservationParameterException("예약 내용에 누락된 부분이 있습니다.");
        }

        Long timeId = reservationRequest.getTime();
        Time time = findTimeById(timeId);

        Reservation reservation = new Reservation(
                reservationRequest.getName(),
                reservationRequest.getDate(),
                time
        );

        String sql = "INSERT INTO reservation (name,date,time_id) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setLong(3, time.getId());
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            reservation.setId(generatedId.longValue());
        }
        return reservation;
    }

    private Time findTimeById(Long timeId) {
        String sql = "SELECT id, time FROM time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Time(
                rs.getLong("id"),
                rs.getString("time")
        ), timeId);
    }

    public void deleteReservation(Long id) {
        int rowAffected = reservationRepository.deleteById(id);
        if (rowAffected == 0) {
            throw new NotFoundReservationException("삭제하려는 예약이 없습니다.");
        }
    }
}
