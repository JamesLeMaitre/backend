package bj.gouv.sineb.ddbservice.application.code.mapper.aae;


import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.CiviliteRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.CiviliteResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.aae.Civilite;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class CiviliteMapper {

    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public CiviliteMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }


    public CiviliteResponse toDto(Civilite entity) {
        CiviliteResponse response = new CiviliteResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    public Civilite toEntity(CiviliteRequest request){
        Civilite civilite = new Civilite();
        civilite.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(Civilite.class.getSimpleName()));
        civilite.setDateCreated(LocalDateTime.now());
        civilite.setDateUpdated(LocalDateTime.now());
        BeanUtils.copyProperties(request, civilite);
        return civilite;
    }

}
