package bj.gouv.sineb.ddbservice.application.code.mapper.rc_rbdd;

import bj.gouv.sineb.ddbservice.application.code.dto.request.rc_rbdd.TypeRessourceDocumentaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.rc_rbdd.TypeRessourceDocumentaireResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.rc_rbdd.PorteeRessourceDocumentaire;
import bj.gouv.sineb.ddbservice.application.code.entity.rc_rbdd.TypeRessourceDocumentaire;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TypeRessourceDocumentaireMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public TypeRessourceDocumentaireMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public TypeRessourceDocumentaireResponse toDto(TypeRessourceDocumentaire entity){
        return TypeRessourceDocumentaireResponse.builder()
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

    public TypeRessourceDocumentaire toEntity(TypeRessourceDocumentaireRequest request){
        TypeRessourceDocumentaire typeRessourceDocumentaire = new TypeRessourceDocumentaire();
        typeRessourceDocumentaire.setId(UUID.randomUUID());
        typeRessourceDocumentaire.setTrackingId(UUID.randomUUID());
        typeRessourceDocumentaire.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(TypeRessourceDocumentaire.class.getSimpleName()));
        typeRessourceDocumentaire.setName(request.getName());
        typeRessourceDocumentaire.setDescription(request.getDescription());
        return typeRessourceDocumentaire;
    }

}
