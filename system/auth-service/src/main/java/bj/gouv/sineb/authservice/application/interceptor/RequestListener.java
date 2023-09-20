package bj.gouv.sineb.authservice.application.interceptor;

import bj.gouv.sineb.authservice.application.code.dto.request.JournalEventRequest;
import bj.gouv.sineb.authservice.application.code.service.JournalEventService;
import bj.gouv.sineb.authservice.application.code.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.ServletRequestHandledEvent;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


//@Slf4j
//@Component
public class RequestListener {

    private final JournalEventService journalEventService;
    private final UserService userService;

    public RequestListener(JournalEventService journalEventService, UserService userService) {
        this.journalEventService = journalEventService;
        this.userService = userService;
    }


    //@EventListener
    public void handleRequest(ServletRequestHandledEvent event) throws Exception {

        /*if (!userService.globalCredentialNonExpired())
            throw new Exception("One of your permission or your crendentials expired. Please reconnect!");*/

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);

        String requestBody = getStringValue(requestWrapper.getContentAsByteArray(),
                request.getCharacterEncoding());

        JournalEventRequest journalEventRequest = JournalEventRequest.builder()
                .method(request.getMethod()) // trackingId and id are set in dto
                .requestUrl(request.getRequestURL().toString())
                .requestUri(request.getRequestURI())
                .queryString(getQueryParams(request))
                .protocol(request.getProtocol())
                .remoteAddr(request.getRemoteAddr())
                .remotePort(request.getRemotePort())
                .localAddr(request.getLocalAddr())
                .localPort(request.getLocalPort())
                .scheme(request.getScheme())
                .timestamp(event.getTimestamp())
                .timestamp(event.getProcessingTimeMillis())
                .statusCode(event.getStatusCode())
                .body(requestBody)
                .build();

        // SAVE JOURNAL EVENT NOW
        journalEventService.save(journalEventRequest);
    }

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

    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding).trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

}