package bj.gouv.sineb.ddbservice.application.code.mapper.rc_rbdd;

import bj.gouv.sineb.ddbservice.application.code.dto.request.rc_rbdd.PorteeRessourceDocumentaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.rc_rbdd.PorteeRessourceDocumentaireResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.rc_rbdd.NatureRessourceDocumentaire;
import bj.gouv.sineb.ddbservice.application.code.entity.rc_rbdd.PorteeRessourceDocumentaire;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PorteeRessourceDocumentaireMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public PorteeRessourceDocumentaireMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public PorteeRessourceDocumentaireResponse toDto(PorteeRessourceDocumentaire entity){
        return PorteeRessourceDocumentaireResponse.builder()
                .trackingId(entity.getTrackingId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdBy(entity.getCreatedBy())
                .codeAuto(entity.getCodeAuto())
                .createdAt(entity.getDateCreated())
                .lastModifiedBy(entity.getUpdatedBy())
                .lastModifiedAt(entity.getDateUpdated())
                .build();
    }

    public PorteeRessourceDocumentaire toEntity(PorteeRessourceDocumentaireRequest request){
        PorteeRessourceDocumentaire porteeRessourceDocumentaire = new PorteeRessourceDocumentaire();
        porteeRessourceDocumentaire.setId(UUID.randomUUID());
        porteeRessourceDocumentaire.setTrackingId(UUID.randomUUID());
        porteeRessourceDocumentaire.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(PorteeRessourceDocumentaire.class.getSimpleName()));
        porteeRessourceDocumentaire.setName(request.getName());
        porteeRessourceDocumentaire.setDescription(request.getDescription());
        return porteeRessourceDocumentaire;
    }

}
