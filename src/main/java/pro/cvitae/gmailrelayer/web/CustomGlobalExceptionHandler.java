/**
 * g-mail-relayer smtp mail relayer and API for sending emails
 * Copyright (C) 2020  https://github.com/betler
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package pro.cvitae.gmailrelayer.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author https://mkyong.com/spring-boot/spring-rest-validation-example/
 *
 */
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * error handle for @Valid
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
            final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        final Map<String, Object> body = this.getDefaultBody(status);

        List<String> errors;
        if (ex.getBindingResult().hasFieldErrors()) {
            // Field errors for field annotations
            errors = ex.getBindingResult().getFieldErrors().stream()
                    .map(fe -> fe.getField() + " " + fe.getDefaultMessage()).collect(Collectors.toList());
        } else {
            // Global errors for class annotations
            errors = ex.getBindingResult().getAllErrors().stream()
                    .map(oe -> oe.getObjectName() + " " + oe.getDefaultMessage()).collect(Collectors.toList());

        }

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
            final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        final Map<String, Object> body = this.getDefaultBody(status);
        final List<String> errors = new ArrayList<>(1);
        errors.add(ex.getMessage());
        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);
    }

    private Map<String, Object> getDefaultBody(final HttpStatus status) {
        final Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        return body;
    }

    /**
     * Builds a {@link ResponseEntity} with the given body and a
     * {@link HttpStatus#INTERNAL_SERVER_ERROR} status code
     *
     * @param <T>
     * @param body
     * @return
     */
    @SuppressWarnings("unused")
    private <T> ResponseEntity<T> error500(final T body) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

}