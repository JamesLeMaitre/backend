package bj.gouv.sineb.ddbservice.application.code.mapper.common;

import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.FormeEnergieSecondaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.FormeEnergieSecondaireResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Etat;
import bj.gouv.sineb.ddbservice.application.code.entity.common.FormeEnergiePrimaire;
import bj.gouv.sineb.ddbservice.application.code.entity.common.FormeEnergieSecondaire;
import bj.gouv.sineb.ddbservice.application.code.repository.common.FormeEnergiePrimaireRepository;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class FormeEnergieSecondaireMapper {

    private final FormeEnergiePrimaireMapper formeEnergiePrimaireMapper;
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;
    private final FormeEnergiePrimaireRepository formeEnergiePrimaireRepository;

    public FormeEnergieSecondaireMapper(FormeEnergiePrimaireMapper formeEnergiePrimaireMapper,
                                        EntityDataCodeGenerationService entityDataCodeGenerationService,
                                        FormeEnergiePrimaireRepository formeEnergiePrimaireRepository) {
        this.formeEnergiePrimaireMapper = formeEnergiePrimaireMapper;
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
        this.formeEnergiePrimaireRepository = formeEnergiePrimaireRepository;
    }

    public FormeEnergieSecondaireResponse toDto(FormeEnergieSecondaire entity){
        return FormeEnergieSecondaireResponse.builder()
                .trackingId(entity.getTrackingId())

                .name(entity.getName())
                .description(entity.getDescription())
                .formeEnergiePrimaireResponse(formeEnergiePrimaireMapper.toDto(entity.getFormeEnergiePrimaire()))
                .createdBy(entity.getCreatedBy())
                .createdAt(entity.getDateCreated())
                .codeAuto(entity.getCodeAuto())
                .lastModifiedBy(entity.getUpdatedBy())
                .lastModifiedAt(entity.getDateUpdated())
                .build();
    }

    public FormeEnergieSecondaire toEntity(FormeEnergieSecondaireRequest request){
        Optional<FormeEnergiePrimaire> formeEnergiePrimaireOptional = formeEnergiePrimaireRepository.findByTrackingId(request.getFormeEnergiePrimaireTrackingId());
        if (formeEnergiePrimaireOptional.isEmpty())
            throw new CustomNotFoundException("FormeEnergiePrimaire with trackingId: "+request.getFormeEnergiePrimaireTrackingId()+" not found !");

        FormeEnergieSecondaire formeEnergieSecondaire = new FormeEnergieSecondaire();
        formeEnergieSecondaire.setId(UUID.randomUUID());
        formeEnergieSecondaire.setTrackingId(UUID.randomUUID());
        formeEnergieSecondaire.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(FormeEnergieSecondaire.class.getSimpleName()));
        formeEnergieSecondaire.setName(request.getName());
        formeEnergieSecondaire.setDescription(request.getDescription());
        return formeEnergieSecondaire;
    }

}
