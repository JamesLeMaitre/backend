package bj.gouv.sineb.ddbservice.application.code.mapper.aae;


import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.CiviliteRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.FormeJuridiqueActeurRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.FormeJuridiqueActeurResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.aae.Civilite;
import bj.gouv.sineb.ddbservice.application.code.entity.aae.FormeJuridiqueActeur;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;


@Component
public class FormeJuridiqueActeurMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public FormeJuridiqueActeurMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public FormeJuridiqueActeurResponse toDto(FormeJuridiqueActeur entity){
        return FormeJuridiqueActeurResponse.builder()
                .trackingId(entity.getTrackingId())
                .codeAuto(entity.getCodeAuto())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdBy(entity.getCreatedBy())
                .createdAt(Instant.from(entity.getDateCreated()))
                .lastModifiedBy(entity.getUpdatedBy())
                .lastModifiedAt(Instant.from(entity.getDateUpdated()))
                .build();
    }



    /**
     * Converts a FormeJuridiqueActeurRequest object to a FormeJuridiqueActeur entity.
     *
     * @param request the FormeJuridiqueActeurRequest object to be converted
     * @return the converted FormeJuridiqueActeur entity
     */
    public FormeJuridiqueActeur toEntity(FormeJuridiqueActeurRequest request) {
        // Create a new FormeJuridiqueActeur entity
        FormeJuridiqueActeur formeJuridiqueActeur = new FormeJuridiqueActeur();

        // Set the id of the entity to a randomly generated UUID
        formeJuridiqueActeur.setId(UUID.randomUUID());

        // Set the trackingId of the entity to a randomly generated UUID
        formeJuridiqueActeur.setTrackingId(UUID.randomUUID());

        // Generate an auto code for the entity and set it
        formeJuridiqueActeur.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(FormeJuridiqueActeur.class.getSimpleName()));

        // Set the name of the entity to the name from the request object
        formeJuridiqueActeur.setName(request.getName());

        // Copy the properties from the request object to the entity object
        BeanUtils.copyProperties(request, formeJuridiqueActeur);

        // Return the converted entity
        return formeJuridiqueActeur;
    }

}
