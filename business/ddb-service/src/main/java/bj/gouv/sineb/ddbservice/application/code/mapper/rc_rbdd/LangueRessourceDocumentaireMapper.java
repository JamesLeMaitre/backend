package bj.gouv.sineb.ddbservice.application.code.mapper.rc_rbdd;


import bj.gouv.sineb.ddbservice.application.code.dto.request.rc_rbdd.LangueRessourceDocumentaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.rc_rbdd.LangueRessourceDocumentaireResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.rc_rbdd.FormatRessourceDocumentaire;
import bj.gouv.sineb.ddbservice.application.code.entity.rc_rbdd.LangueRessourceDocumentaire;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LangueRessourceDocumentaireMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public LangueRessourceDocumentaireMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public LangueRessourceDocumentaireResponse toDto(LangueRessourceDocumentaire entity){
        return LangueRessourceDocumentaireResponse.builder()
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

    public LangueRessourceDocumentaire toEntity(LangueRessourceDocumentaireRequest request){
        LangueRessourceDocumentaire langueRessourceDocumentaire = new LangueRessourceDocumentaire();
        langueRessourceDocumentaire.setId(UUID.randomUUID());
        langueRessourceDocumentaire.setTrackingId(UUID.randomUUID());
        langueRessourceDocumentaire.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(LangueRessourceDocumentaire.class.getSimpleName()));
        langueRessourceDocumentaire.setName(request.getName());
        langueRessourceDocumentaire.setDescription(request.getDescription());
        return langueRessourceDocumentaire;
    }

}
