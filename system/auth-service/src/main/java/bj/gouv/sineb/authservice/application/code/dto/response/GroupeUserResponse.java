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
public class GroupeUserResponse {
    private UUID trackingId;
    private UserResponse userResponse;
    private GroupeResponse groupeResponse;
    private String createdBy;
    private Instant createdAt;
    private String lastModifiedBy;
    private Instant lastModifiedAt;
}
