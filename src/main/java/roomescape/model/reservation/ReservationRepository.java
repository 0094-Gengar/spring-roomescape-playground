package roomescape.model.reservation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.model.time.Time;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = """
            SELECT 
                r.id as reservation_id, 
                r.name, 
                r.date, 
                t.id as time_id, 
                t.time as time_value 
            FROM reservation as r 
            INNER JOIN time as t ON r.time_id = t.id
        """;

        return jdbcTemplate.query(sql, new ReservationRowMapper());
    }

    private static class ReservationRowMapper implements RowMapper<Reservation> {
        @Override
        public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {

            Long timeId  = rs.getLong("time_id");
            String timeValue = rs.getString("time_value");

            Time time = new Time(timeValue);

            Reservation reservation = new Reservation(
                    rs.getString("name"),
                    rs.getString("date"),
                    time
            );
            reservation.setId(rs.getLong("reservation_id"));
            return reservation;
        }
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
