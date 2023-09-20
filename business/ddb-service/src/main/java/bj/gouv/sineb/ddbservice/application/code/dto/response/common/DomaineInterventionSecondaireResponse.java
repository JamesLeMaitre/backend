package bj.gouv.sineb.ddbservice.application.code.dto.response.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DomaineInterventionSecondaireResponse implements Serializable {
    private UUID trackingId;
    private String codeAuto;
    private String name;
    private String description;
    private DomaineInterventionPrincipaleResponse domaineInterventionPrincipaleResponse;
    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;
}
