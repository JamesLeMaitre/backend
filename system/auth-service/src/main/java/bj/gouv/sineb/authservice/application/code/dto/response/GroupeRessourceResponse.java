package bj.gouv.sineb.authservice.application.code.dto.response;

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
public class GroupeRessourceResponse {
    private UUID trackingId;
    private GroupeResponse groupeResponse;
    private RessourceResponse ressourceResponse;
    private String createdBy;
    private Instant createdAt;
    private String lastModifiedBy;
    private Instant lastModifiedAt;
    private List<GroupeRessourceScopeResponse> groupeRessourceScopeResponseList = new CopyOnWriteArrayList<>();

}
