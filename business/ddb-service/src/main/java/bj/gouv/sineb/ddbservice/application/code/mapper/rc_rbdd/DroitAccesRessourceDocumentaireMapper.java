package bj.gouv.sineb.ddbservice.application.code.mapper.rc_rbdd;


import bj.gouv.sineb.ddbservice.application.code.dto.request.rc_rbdd.DroitAccesRessourceDocumentaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.rc_rbdd.DroitAccesRessourceDocumentaireResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.rc_rbdd.DroitAccesRessourceDocumentaire;
import bj.gouv.sineb.ddbservice.application.code.entity.sie.BrancheActiviteEconomique;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DroitAccesRessourceDocumentaireMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public DroitAccesRessourceDocumentaireMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public DroitAccesRessourceDocumentaireResponse toDto(DroitAccesRessourceDocumentaire entity){
        return DroitAccesRessourceDocumentaireResponse.builder()
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

    public DroitAccesRessourceDocumentaire toEntity(DroitAccesRessourceDocumentaireRequest request){
        DroitAccesRessourceDocumentaire droitAccesRessourceDocumentaire = new DroitAccesRessourceDocumentaire();
        droitAccesRessourceDocumentaire.setId(UUID.randomUUID());
        droitAccesRessourceDocumentaire.setTrackingId(UUID.randomUUID());
        droitAccesRessourceDocumentaire.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(DroitAccesRessourceDocumentaire.class.getSimpleName()));
        droitAccesRessourceDocumentaire.setName(request.getName());
        droitAccesRessourceDocumentaire.setDescription(request.getDescription());
        return droitAccesRessourceDocumentaire;
    }

}
