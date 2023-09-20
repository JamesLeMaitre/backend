package bj.gouv.sineb.ddbservice.application.code.mapper.common;

import bj.gouv.sineb.ddbservice.application.code.dto.request.common.DomaineInterventionPrincipaleRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.DomaineInterventionPrincipaleResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Departement;
import bj.gouv.sineb.ddbservice.application.code.entity.common.DomaineInterventionPrincipale;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DomaineInterventionPrincipaleMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public DomaineInterventionPrincipaleMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public DomaineInterventionPrincipaleResponse toDto(DomaineInterventionPrincipale entity){
        return DomaineInterventionPrincipaleResponse.builder()
                .trackingId(entity.getTrackingId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdBy(entity.getCreatedBy())
                .createdAt(entity.getDateCreated())
                .codeAuto(entity.getCodeAuto())
                .lastModifiedBy(entity.getUpdatedBy())
                .lastModifiedAt(entity.getDateUpdated())
                .build();
    }

    public DomaineInterventionPrincipale toEntity(DomaineInterventionPrincipaleRequest request){
        DomaineInterventionPrincipale principale = new DomaineInterventionPrincipale();
        principale.setId(UUID.randomUUID());
        principale.setTrackingId(UUID.randomUUID());
        principale.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(DomaineInterventionPrincipale.class.getSimpleName()));
        principale.setName(request.getName());
        principale.setDescription(request.getDescription());
        return principale;
    }

}
