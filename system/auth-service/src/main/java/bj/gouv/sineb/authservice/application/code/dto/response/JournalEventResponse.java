package bj.gouv.sineb.authservice.application.code.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JournalEventResponse {
    private UUID trackingId;
    private String userAgent;
    private String contentType;
    private String contentLength;
    private String method;
    private String requestUrl;
    private String requestUri;
    private String queryString;
    private String protocol;
    private String remoteAddr;
    private int remotePort;
    private String localAddr;
    private int localPort;
    private String scheme;
    private long timestamp;
    private long processingTimeMillis;
    private int statusCode;
    private String body;
    private String createdBy;
    private String userOfActionTrackingId;
    private Instant createdAt;
    private UserResponse userResponse;
}
