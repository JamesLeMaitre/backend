package bj.gouv.sineb.ddbservice.application.code.mapper.common;


import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.DomaineInterventionSecondaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.DomaineInterventionSecondaireResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Commune;
import bj.gouv.sineb.ddbservice.application.code.entity.common.DomaineInterventionPrincipale;
import bj.gouv.sineb.ddbservice.application.code.entity.common.DomaineInterventionSecondaire;
import bj.gouv.sineb.ddbservice.application.code.repository.common.DomaineInterventionPrincipaleRepository;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class DomaineInterventionSecondaireMapper {

    private final DomaineInterventionPrincipaleMapper domaineInterventionPrincipaleMapper;
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;
    private final DomaineInterventionPrincipaleRepository domaineInterventionPrincipaleRepository;


    public DomaineInterventionSecondaireMapper(DomaineInterventionPrincipaleMapper domaineInterventionPrincipaleMapper,
                                               EntityDataCodeGenerationService entityDataCodeGenerationService,
                                               DomaineInterventionPrincipaleRepository domaineInterventionPrincipaleRepository) {
        this.domaineInterventionPrincipaleMapper = domaineInterventionPrincipaleMapper;
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
        this.domaineInterventionPrincipaleRepository = domaineInterventionPrincipaleRepository;
    }



    public DomaineInterventionSecondaireResponse toDto(DomaineInterventionSecondaire entity){
        return DomaineInterventionSecondaireResponse.builder()
                .trackingId(entity.getTrackingId())

                .name(entity.getName())
                .description(entity.getDescription())
                .domaineInterventionPrincipaleResponse(domaineInterventionPrincipaleMapper.toDto(entity.getDomaineInterventionPrincipale()))
                .createdBy(entity.getCreatedBy())
                .createdAt(entity.getDateCreated())
                .codeAuto(entity.getCodeAuto())
                .lastModifiedBy(entity.getUpdatedBy())
                .lastModifiedAt(entity.getDateUpdated())
                .build();
    }

    public DomaineInterventionSecondaire toEntity(DomaineInterventionSecondaireRequest request){
        Optional<DomaineInterventionPrincipale> domaineInterventionPrincipaleOptional = domaineInterventionPrincipaleRepository.findByTrackingId(request.getDomaineInterventionPrincipale());
        if (domaineInterventionPrincipaleOptional.isEmpty())
            throw new CustomNotFoundException("DomaineInterventionPrincipale with trackingId: "+request.getDomaineInterventionPrincipale()+" not found !");


        DomaineInterventionSecondaire secondaire = new DomaineInterventionSecondaire();
        secondaire.setId(UUID.randomUUID());
        secondaire.setTrackingId(UUID.randomUUID());
        secondaire.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(DomaineInterventionSecondaire.class.getSimpleName()));
        secondaire.setName(request.getName());
        secondaire.setDescription(request.getDescription());
        return secondaire;

    }

}
