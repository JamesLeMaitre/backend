package bj.gouv.sineb.ddbservice.application.code.mapper.common;

import bj.gouv.sineb.ddbservice.application.code.dto.request.common.CommuneRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.CommuneResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Comite;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Commune;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CommuneMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public CommuneMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public CommuneResponse toDto(Commune entity){
        return CommuneResponse.builder()
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

    public Commune toEntity(CommuneRequest request){
        Commune commune = new Commune();
        commune.setId(UUID.randomUUID());
        commune.setTrackingId(UUID.randomUUID());
        commune.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(Commune.class.getSimpleName()));
        commune.setName(request.getName());
        commune.setDescription(request.getDescription());
        return commune;
    }

}
