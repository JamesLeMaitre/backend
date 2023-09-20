package bj.gouv.sineb.authservice.application.code.dto.request;

import jakarta.validation.constraints.NotNull;
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
public class PolicieUpdateRequest {
    private UUID trackingId;
    @NotNull(message = "endDate is required.")
    private Instant startDate; // Not always required. we'll be checked on backend side
    @NotNull(message = "endDate is required.")
    private Instant endDate;
    @NotNull(message = "User id is required.")
    private UUID userTrackingId;
    @NotNull(message = "Ressource id is required.")
    private UUID ressourceTrackingId;
    @NotNull(message = "Scope id is required.")
    private UUID scopeTrackingId;
}
