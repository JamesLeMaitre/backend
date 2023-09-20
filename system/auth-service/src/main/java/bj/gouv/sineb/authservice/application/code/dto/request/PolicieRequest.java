package bj.gouv.sineb.authservice.application.code.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PolicieRequest {
    private UUID trackingId;
    private Instant startDate; // Not always required. we'll be checked on backend side
    @NotNull(message = "endDate is required.")
    private Instant endDate;
    private boolean takeEffectNow;
    @NotNull(message = "User id is required.")
    private UUID userTrackingId;
    @NotNull(message = "Ressource id is required.")
    private UUID ressourceTrackingId;
    @NotNull(message = "Scopes ids is required.")
    private List<UUID> scopeTrackingIds = new CopyOnWriteArrayList<>();
}
