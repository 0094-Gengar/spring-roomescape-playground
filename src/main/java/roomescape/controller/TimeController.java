package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.model.time.Time;
import roomescape.model.time.TimeRequest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller
public class TimeController {
    private final JdbcTemplate jdbcTemplate;

    public TimeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/times")
    @ResponseBody
    public List<Time> getTimes() {
        String sql = "SELECT * FROM time";
        return jdbcTemplate.query(sql, new TimeRowMapper());
    }

    private static class TimeRowMapper implements RowMapper<Time> {
        @Override
        public Time mapRow(ResultSet rs,  int rowNum) throws SQLException {
            Time time = new Time(rs.getString("time"));
            time.setId(rs.getLong("id"));
            return time;
        }
    }

    @PostMapping("/times")
    public ResponseEntity<Time> createTimes(@RequestBody TimeRequest timeRequest) {
        Time time = new Time(timeRequest.getTime());

        String sql = "INSERT INTO time (time) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, time.getTime());
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            time.setId(generatedId.longValue());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/times/" + time.getId())
                .body(time);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        return ResponseEntity.noContent().build();
    }
}
