package bj.gouv.sineb.ddbservice.application.code.mapper.common;


import bj.gouv.sineb.ddbservice.application.code.dto.request.common.ComiteRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.ComiteResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.CategoryElt;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Comite;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ComiteMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public ComiteMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public ComiteResponse toDto(Comite entity){
        return ComiteResponse.builder()
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

    public Comite toEntity(ComiteRequest request){
        Comite comite = new Comite();
        comite.setId(UUID.randomUUID());
        comite.setTrackingId(UUID.randomUUID());
        comite.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(Comite.class.getSimpleName()));
        comite.setName(request.getName());
        comite.setDescription(request.getDescription());
        return comite;

    }

}
