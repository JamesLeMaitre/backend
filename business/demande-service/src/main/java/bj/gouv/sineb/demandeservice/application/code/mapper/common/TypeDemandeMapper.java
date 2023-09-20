package bj.gouv.sineb.demandeservice.application.code.mapper.common;

import bj.gouv.sineb.demandeservice.application.code.entity.common.TypeDemande;
import bj.gouv.sineb.demandeservice.application.code.utils.EntityDataCodeGenerationService;
import bj.gouv.sineb.demandeservice.common.dto.request.common.TypeDemandeRequest;
import bj.gouv.sineb.demandeservice.common.dto.response.common.TypeDemandeResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TypeDemandeMapper {

    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public TypeDemandeMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public TypeDemandeResponse toDto(TypeDemande typeDemande) {
        TypeDemandeResponse response = new TypeDemandeResponse();
        BeanUtils.copyProperties(typeDemande, response);
        return response;
    }

    public TypeDemande toEntity(TypeDemandeRequest request) {
        TypeDemande typeDemande = new TypeDemande();
        typeDemande.setTrackingId(UUID.randomUUID());
        typeDemande.setDateCreated(LocalDateTime.now());
        typeDemande.setDateUpdated(LocalDateTime.now());
        typeDemande.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(TypeDemande.class.getSimpleName()));
        BeanUtils.copyProperties(request, typeDemande);
        return typeDemande;
    }

    public TypeDemande toEntity(TypeDemandeRequest request, TypeDemande typeDemande) {
        typeDemande.setDateUpdated(LocalDateTime.now());
        BeanUtils.copyProperties(request, typeDemande);
        return typeDemande;
    }
    
}
