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
public class VerificationTokenResponse {
    private UUID trackingId;
    private boolean called;
    private String token;
    private String createdBy;
    private Instant createdAt;
    private String lastModifiedBy;
    private Instant lastModifiedAt;
    private UserResponse userResponse;
}
