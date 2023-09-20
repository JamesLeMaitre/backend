package bj.gouv.sineb.authservice.application.code.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GroupeRessourceScopeRequest {
    private UUID trackingId;
    @NotNull(message = "Groupe id is required.")
    private UUID groupeTrackingId;
    @NotNull(message = "Ressource id is required.")
    private UUID ressourceTrackingId;
    @NotNull(message = "scopeIds are required.")
    @NotNull(message = "one scope at least is required.")
    private List<UUID> scopeTrackingIds = new CopyOnWriteArrayList<>();;

}
