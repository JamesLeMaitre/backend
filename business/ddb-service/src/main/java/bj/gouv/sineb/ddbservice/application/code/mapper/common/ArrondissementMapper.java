package bj.gouv.sineb.ddbservice.application.code.mapper.common;



import bj.gouv.sineb.ddbservice.application.code.dto.request.common.ArrondissementRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.ArrondissementResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Arrondissement;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ArrondissementMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public ArrondissementMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public ArrondissementResponse toDto(Arrondissement entity){
        return ArrondissementResponse.builder()
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

    /**
     *  Convert a ArrondissementRequest object to a Arrondissement entity.
     * @param request
     * @return
     */
    public Arrondissement toEntity(ArrondissementRequest request){
        Arrondissement arrondissement = new Arrondissement();
        arrondissement.setId(UUID.randomUUID());
        arrondissement.setTrackingId(UUID.randomUUID());
        arrondissement.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(Arrondissement.class.getSimpleName()));
        arrondissement.setName(request.getName());
        arrondissement.setDescription(request.getDescription());
        BeanUtils.copyProperties(request, arrondissement);
        return arrondissement;
    }

}
