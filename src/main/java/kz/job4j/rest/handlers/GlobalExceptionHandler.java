package kz.job4j.rest.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.job4j.rest.model.response.ResultMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {
    private final ObjectMapper objectMapper;

    @ExceptionHandler(value = {NullPointerException.class})
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(
                ResultMessage.error(e.getMessage())
        ));
        log.error(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultMessage<String>> handle(MethodArgumentNotValidException e) {
        String errorMessage = e.getFieldErrors().stream()
                .map(f -> String.format("%s. Actual value: %s", f.getDefaultMessage(), f.getRejectedValue()))
                .collect(Collectors.joining("; "));
        return ResponseEntity.badRequest().body(
                ResultMessage.error(errorMessage)
        );
    }
}
