package bj.gouv.sineb.demandeservice.application.event;



import bj.gouv.sineb.common.dto.request.JournalEventRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class AuditEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public AuditEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publish(String body, Instant startProcessing, Instant endProcessing, int statusCode){

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        /*Map<String, String> dataMap = new LinkedHashMap<>();
        dataMap.put("host", request.getHeader("host"));
        dataMap.put("email-agent", request.getHeader("User-Agent"));
        dataMap.put("message", message);*/

        JournalEventRequest journalEventRequest = JournalEventRequest.builder()
                .userAgent(request.getHeader("User-Agent"))
                .contentType(request.getHeader("Content-Type"))
                .contentLength(request.getHeader("Content-Length"))
                .method(request.getMethod())
                .requestUrl(request.getRequestURL().toString())
                .requestUri(request.getRequestURI())
                .queryString(getQueryParams(request))
                .protocol(request.getProtocol())
                .remoteAddr(request.getRemoteAddr())
                .remotePort(request.getRemotePort())
                .localAddr(request.getLocalAddr())
                .localPort(request.getLocalPort())
                .scheme(request.getScheme())
                .timestamp(startProcessing.getEpochSecond())
                .processingTimeMillis(endProcessing.getEpochSecond()-startProcessing.getEpochSecond())
                .statusCode(statusCode)
                .body(body)
                .build();

        // Publish JournalEventRequest object here
        applicationEventPublisher.publishEvent(new AuditEvent<>(journalEventRequest));
    }

    //  Accept: The MIME types accepted by the client.
    //  Accept-Encoding: The content encodings accepted by the client.
    //  Accept-Language: The natural languages preferred by the client.
    //  Cache-Control: Specifies caching directives.
    //  Content-Length: The length of the request body in bytes.
    //  Content-Type: The media type of the request body.
    //  Host: The hostname and port number of the server.
    //  User-Agent: The email agent string of the client's browser or email agent.

    private String getQueryParams(HttpServletRequest request){
        Map<String, String> queryParamsMap = new HashMap<>();
        for (String parameterName : request.getParameterMap().keySet()) {
            String[] values = request.getParameterMap().get(parameterName);
            if (values != null && values.length > 0) {
                queryParamsMap.put(parameterName, values[0]);
            } else {
                queryParamsMap.put(parameterName, null);
            }
        }
        return queryParamsMap.toString();
    }

}
