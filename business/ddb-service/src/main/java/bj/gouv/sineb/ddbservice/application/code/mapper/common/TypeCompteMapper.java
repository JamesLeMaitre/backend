package bj.gouv.sineb.ddbservice.application.code.mapper.common;

import bj.gouv.sineb.ddbservice.application.code.dto.request.common.TypeCompteRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.TypeCompteResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.TypeCompte;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TypeCompteMapper {

    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public TypeCompteMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public TypeCompteResponse toDto(TypeCompte typeCompte) {
        TypeCompteResponse response = new TypeCompteResponse();
        BeanUtils.copyProperties(typeCompte, response);
        return response;
    }

    public TypeCompte toEntity(TypeCompteRequest request) {
        TypeCompte typeCompte = new TypeCompte();
        typeCompte.setTrackingId(UUID.randomUUID());
        typeCompte.setDateCreated(LocalDateTime.now());
        typeCompte.setDateUpdated(LocalDateTime.now());
        typeCompte.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(TypeCompte.class.getSimpleName()));
        BeanUtils.copyProperties(request, typeCompte);
        return typeCompte;
    }

    public TypeCompte toEntity(TypeCompteRequest request, TypeCompte typeCompte) {
        typeCompte.setDateUpdated(LocalDateTime.now());
        BeanUtils.copyProperties(request, typeCompte);
        return typeCompte;
    }

}
