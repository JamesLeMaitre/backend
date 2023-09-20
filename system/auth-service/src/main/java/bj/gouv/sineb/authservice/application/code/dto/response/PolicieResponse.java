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
public class PolicieResponse {
    private UUID trackingId;
    private boolean expired;
    private Instant startDate;
    private Instant endDate;
    private boolean takeEffectNow;
    private String createdBy;
    private Instant createdAt;
    private String lastModifiedBy;
    private Instant lastModifiedAt;
    private UserResponse userResponse;
    private RessourceResponse ressourceResponse;
    private ScopeResponse scopeResponse;

    /*@NotNull(event = "Scopes name is required.")
    private List<ScopeResponse> scopeResponseList = new CopyOnWriteArrayList<>();*/
}
