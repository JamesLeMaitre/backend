package bj.gouv.sineb.ddbservice.application.code.mapper.common;


import bj.gouv.sineb.ddbservice.application.code.dto.request.common.EtatRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.EtatResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Commune;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Etat;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EtatMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public EtatMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public EtatResponse toDto(Etat entity){
        return EtatResponse.builder()
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

    public Etat toEntity(EtatRequest request){
        Etat etat = new Etat();
        etat.setId(UUID.randomUUID());
        etat.setTrackingId(UUID.randomUUID());
        etat.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(Etat.class.getSimpleName()));
        etat.setName(request.getName());
        etat.setDescription(request.getDescription());
        return etat;

    }

}
