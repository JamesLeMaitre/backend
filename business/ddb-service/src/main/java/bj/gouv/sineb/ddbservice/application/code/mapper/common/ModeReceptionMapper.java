package bj.gouv.sineb.ddbservice.application.code.mapper.common;


import bj.gouv.sineb.ddbservice.application.code.dto.request.common.ModeReceptionRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.ModeReceptionResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Etat;
import bj.gouv.sineb.ddbservice.application.code.entity.common.ModeReception;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ModeReceptionMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public ModeReceptionMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public ModeReceptionResponse toDto(ModeReception entity){
        return ModeReceptionResponse.builder()
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

    public ModeReception toEntity(ModeReceptionRequest request){
        ModeReception modeReception = new ModeReception();
        modeReception.setId(UUID.randomUUID());
        modeReception.setTrackingId(UUID.randomUUID());
        modeReception.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(ModeReception.class.getSimpleName()));
        modeReception.setName(request.getName());
        modeReception.setDescription(request.getDescription());
        return modeReception;
    }

}
