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
    private final ReservationDAO reservationDAO;

    @Autowired
    public ReservationService(JdbcTemplate jdbcTemplate, ReservationDAO reservationDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationDAO = reservationDAO;
    }

    public List<Reservation> getAllReservations() {
        return reservationDAO.findAll();
    }

    public Reservation addReservation(ReservationDTO reservationDTO) {
        if (reservationDTO.getName() == null || reservationDTO.getName().isEmpty() ||
                reservationDTO.getDate() == null || reservationDTO.getDate().isEmpty() ||
                reservationDTO.getTime() == null) {
            throw new InvalidReservationParameterException("예약 내용에 누락된 부분이 있습니다.");
        }

        Long timeId = reservationDTO.getTime();
        Time time = findTimeById(timeId);

        Reservation reservation = new Reservation(
                reservationDTO.getName(),
                reservationDTO.getDate(),
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
        int rowAffected = reservationDAO.deleteById(id);
        if (rowAffected == 0) {
            throw new NotFoundReservationException("삭제하려는 예약이 없습니다.");
        }
    }
}
