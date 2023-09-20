package bj.gouv.sineb.ddbservice.application.code.mapper.aae;

import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.TypeStructureRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.TypeStructureResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.aae.TypeStructure;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TypeStructureMapper {

    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public TypeStructureMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }


    public TypeStructureResponse toDto(TypeStructure typeStructure) {
        TypeStructureResponse response = new TypeStructureResponse();
        BeanUtils.copyProperties(typeStructure, response);
        return response;
    }

    public TypeStructure toEntity(TypeStructureRequest request) {
        TypeStructure typeStructure = new TypeStructure();
        typeStructure.setTrackingId(UUID.randomUUID());
        typeStructure.setDateCreated(LocalDateTime.now());
        typeStructure.setDateUpdated(LocalDateTime.now());
        typeStructure.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(TypeStructure.class.getSimpleName()));
        BeanUtils.copyProperties(request, typeStructure);
        return typeStructure;
    }

    public TypeStructure toEntity(TypeStructureRequest request, TypeStructure typeStructure) {
        typeStructure.setDateUpdated(LocalDateTime.now());
        BeanUtils.copyProperties(request, typeStructure);
        return typeStructure;
    }
}
