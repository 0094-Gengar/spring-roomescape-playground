package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.model.time.Time;
import roomescape.model.time.TimeRequest;

import java.sql.PreparedStatement;

@Controller
public class TimeController {
    private final JdbcTemplate jdbcTemplate;

    public TimeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
}
