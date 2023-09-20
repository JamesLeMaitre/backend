package bj.gouv.sineb.ddbservice.application.code.mapper.common;

import bj.gouv.sineb.ddbservice.application.code.dto.request.common.FormeEnergiePrimaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.FormeEnergiePrimaireResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Etat;
import bj.gouv.sineb.ddbservice.application.code.entity.common.FormeEnergiePrimaire;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FormeEnergiePrimaireMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public FormeEnergiePrimaireMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public FormeEnergiePrimaireResponse toDto(FormeEnergiePrimaire entity){
        return FormeEnergiePrimaireResponse.builder()
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

    public FormeEnergiePrimaire toEntity(FormeEnergiePrimaireRequest request){
        FormeEnergiePrimaire formeEnergiePrimaire = new FormeEnergiePrimaire();
        formeEnergiePrimaire.setId(UUID.randomUUID());
        formeEnergiePrimaire.setTrackingId(UUID.randomUUID());
        formeEnergiePrimaire.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(FormeEnergiePrimaire.class.getSimpleName()));
        formeEnergiePrimaire.setName(request.getName());
        formeEnergiePrimaire.setDescription(request.getDescription());
        return formeEnergiePrimaire;
    }

}
