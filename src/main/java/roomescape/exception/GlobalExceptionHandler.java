package roomescape.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.controller.ReservationController;

@ControllerAdvice(assignableTypes = ReservationController.class)
public class GlobalExceptionHandler {

    @ExceptionHandler({NotFoundReservationException.class, InvalidReservationParameterException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleIllegalArgumentException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST) // status: 400
                .body(exception.getMessage());
    }
}
