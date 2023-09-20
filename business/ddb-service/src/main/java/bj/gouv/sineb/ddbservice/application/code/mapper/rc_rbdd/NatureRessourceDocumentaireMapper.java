package bj.gouv.sineb.ddbservice.application.code.mapper.rc_rbdd;


import bj.gouv.sineb.ddbservice.application.code.dto.request.rc_rbdd.NatureRessourceDocumentaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.rc_rbdd.NatureRessourceDocumentaireResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.rc_rbdd.LangueRessourceDocumentaire;
import bj.gouv.sineb.ddbservice.application.code.entity.rc_rbdd.NatureRessourceDocumentaire;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NatureRessourceDocumentaireMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public NatureRessourceDocumentaireMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public NatureRessourceDocumentaireResponse toDto(NatureRessourceDocumentaire entity){
        return NatureRessourceDocumentaireResponse.builder()
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

    public NatureRessourceDocumentaire toEntity(NatureRessourceDocumentaireRequest request){
        NatureRessourceDocumentaire natureRessourceDocumentaire = new NatureRessourceDocumentaire();
        natureRessourceDocumentaire.setId(UUID.randomUUID());
        natureRessourceDocumentaire.setTrackingId(UUID.randomUUID());
        natureRessourceDocumentaire.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(NatureRessourceDocumentaire.class.getSimpleName()));
        natureRessourceDocumentaire.setName(request.getName());
        natureRessourceDocumentaire.setDescription(request.getDescription());
        return natureRessourceDocumentaire;
    }

}
