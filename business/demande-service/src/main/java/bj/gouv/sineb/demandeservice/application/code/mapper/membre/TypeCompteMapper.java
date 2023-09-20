package bj.gouv.sineb.demandeservice.application.code.mapper.membre;

import bj.gouv.sineb.demandeservice.application.code.entity.membre.TypeCompte;
import bj.gouv.sineb.demandeservice.application.code.utils.EntityDataCodeGenerationService;

import bj.gouv.sineb.demandeservice.common.dto.request.membre.TypeCompteRequest;
import bj.gouv.sineb.demandeservice.common.dto.response.membre.TypeCompteResponse;
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
