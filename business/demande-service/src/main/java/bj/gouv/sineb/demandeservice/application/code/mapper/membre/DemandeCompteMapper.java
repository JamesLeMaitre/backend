package bj.gouv.sineb.demandeservice.application.code.mapper.membre;

import bj.gouv.sineb.demandeservice.application.code.entity.membre.DemandeCompte;
import bj.gouv.sineb.demandeservice.application.code.utils.EntityDataCodeGenerationService;


import bj.gouv.sineb.demandeservice.common.dto.request.membre.DemandeCompteRequest;
import bj.gouv.sineb.demandeservice.common.dto.response.membre.DemandeCompteResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class DemandeCompteMapper {

    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public DemandeCompteMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }
    
    public DemandeCompteResponse toDto(DemandeCompte compte) {
        DemandeCompteResponse response = new DemandeCompteResponse();
        BeanUtils.copyProperties(compte, response);
        return response;
    }

    public DemandeCompte toEntity(DemandeCompteRequest request) {
        DemandeCompte compte = new DemandeCompte();
        compte.setTrackingId(UUID.randomUUID());
        compte.setDateCreated(LocalDateTime.now());
        compte.setDateUpdated(LocalDateTime.now());
        compte.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(DemandeCompte.class.getSimpleName()));
        BeanUtils.copyProperties(request, compte);
        return compte;
    }

    public DemandeCompte toEntity(DemandeCompteRequest request, DemandeCompte compte) {
        compte.setDateUpdated(LocalDateTime.now());
        BeanUtils.copyProperties(request, compte);
        return compte;
    }

}
