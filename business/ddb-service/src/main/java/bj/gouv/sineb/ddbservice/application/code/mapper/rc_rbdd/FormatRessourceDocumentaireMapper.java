package bj.gouv.sineb.ddbservice.application.code.mapper.rc_rbdd;


import bj.gouv.sineb.ddbservice.application.code.dto.request.rc_rbdd.FormatRessourceDocumentaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.rc_rbdd.FormatRessourceDocumentaireResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.rc_rbdd.DroitAccesRessourceDocumentaire;
import bj.gouv.sineb.ddbservice.application.code.entity.rc_rbdd.FormatRessourceDocumentaire;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FormatRessourceDocumentaireMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public FormatRessourceDocumentaireMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public FormatRessourceDocumentaireResponse toDto(FormatRessourceDocumentaire entity){
        return FormatRessourceDocumentaireResponse.builder()
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

    public FormatRessourceDocumentaire toEntity(FormatRessourceDocumentaireRequest request){
        FormatRessourceDocumentaire formatRessourceDocumentaire = new FormatRessourceDocumentaire();
        formatRessourceDocumentaire.setId(UUID.randomUUID());
        formatRessourceDocumentaire.setTrackingId(UUID.randomUUID());
        formatRessourceDocumentaire.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(FormatRessourceDocumentaire.class.getSimpleName()));
        formatRessourceDocumentaire.setName(request.getName());
        formatRessourceDocumentaire.setDescription(request.getDescription());
        return formatRessourceDocumentaire;
    }

}
