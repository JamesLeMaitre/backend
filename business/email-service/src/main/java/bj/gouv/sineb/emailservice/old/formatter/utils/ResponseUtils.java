package bj.gouv.sineb.emailservice.old.formatter.utils;

import bj.gouv.sineb.emailservice.old.formatter.responses.HttpResponse;
import bj.gouv.sineb.emailservice.old.formatter.responses.SuccessResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ResponseUtils {
    public static <T> HttpResponse successResponse(String message, HttpStatusCode status, T data, boolean success) {
        return Optional.of(data).map(t -> SuccessResponse.builder().data(t).build()).stream()
                .peek(successResponse -> successResponse.setMessage(message))
                .peek(successResponse -> successResponse.setStatus(status))
                .peek(successResponse -> successResponse.setSuccess(success))
                .findFirst().orElseThrow();
    }
}
