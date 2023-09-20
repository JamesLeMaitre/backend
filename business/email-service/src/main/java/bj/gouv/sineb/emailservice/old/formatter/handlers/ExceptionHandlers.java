package bj.gouv.sineb.emailservice.old.formatter.handlers;


import bj.gouv.sineb.emailservice.old.exceptions.MailException;
import bj.gouv.sineb.emailservice.old.formatter.responses.ErrorFieldResponse;
import bj.gouv.sineb.emailservice.old.formatter.responses.ErrorResponse;
import bj.gouv.sineb.emailservice.old.formatter.responses.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlers implements ErrorController {

    protected ResponseEntity<HttpResponse> createHttpResponse(HttpStatus status, String message) {
        HttpResponse response = ErrorResponse.builder().reason(status.getReasonPhrase()).build();
        response.setStatus(status);
        response.setMessage(message);
        return new ResponseEntity<>(response, status);
    }

    protected ResponseEntity<HttpResponse> createValidationHttpResponse(String message, List<FieldError> errors) {
        List<ErrorFieldResponse> fieldResponses = errors.stream().map(fieldError -> {
            ErrorFieldResponse response = new ErrorFieldResponse();
            BeanUtils.copyProperties(fieldError, response);
            return response;
        }).toList();
        HttpResponse response = ErrorResponse.builder().reason(HttpStatus.BAD_REQUEST.getReasonPhrase()).validations(fieldResponses).build();
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setMessage(message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<HttpResponse> groupUserNotFoundException(MailException exception) {
        log.error(exception.getMessage());
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }
}
