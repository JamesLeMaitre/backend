package bj.gouv.sineb.ddbservice.application.code.mapper.common;

import bj.gouv.sineb.ddbservice.application.code.dto.request.common.PaysRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.PaysResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Pays;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaysMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public PaysMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public PaysResponse toDto(Pays entity){
        PaysResponse response = new PaysResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    public Pays toEntity(PaysRequest request){
        Pays pays = new Pays();
        pays.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(Pays.class.getSimpleName()));
        BeanUtils.copyProperties(request, pays);
        return pays;
    }

}
