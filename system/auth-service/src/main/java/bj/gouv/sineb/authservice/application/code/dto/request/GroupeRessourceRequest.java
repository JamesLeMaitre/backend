package bj.gouv.sineb.authservice.application.code.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GroupeRessourceRequest {
    private UUID trackingId;
    @NotNull(message = "Groupe id is required.")
    private UUID groupeTrackingId;
    @NotNull(message = "Ressource id is required.")
    private UUID ressourceTrackingId;
}
