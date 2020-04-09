package pro.cvitae.gmailrelayer.web;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import pro.cvitae.gmailrelayer.api.exception.ErrorDetailException;
import pro.cvitae.gmailrelayer.api.model.ErrorDetail;

/**
 * @author https://mkyong.com/spring-boot/spring-rest-validation-example/
 *
 */
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * error handle for @Valid
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
            final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        final Map<String, Object> body = this.getDefaultBody(status);

        // Get all errors
        final List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + " " + fe.getDefaultMessage()).collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }

    protected ResponseEntity<ErrorDetail> handleErrorDetailException(final ErrorDetailException ex,
            final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        this.logger.error("ErrorDetailException", ex);

        return new ResponseEntity<>(ex.getDetail(), headers, status);
    }

    private Map<String, Object> getDefaultBody(final HttpStatus status) {
        final Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        return body;
    }

}