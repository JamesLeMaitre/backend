package bj.gouv.sineb.ddbservice.application.code.mapper.aae;


import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.ProfessionRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.ProfessionResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.aae.Profession;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Slf4j
public class ProfessionMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public ProfessionMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public ProfessionResponse toDto(Profession entity){
        return ProfessionResponse.builder()
                .trackingId(entity.getTrackingId())
                .codeAuto(entity.getCodeAuto())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdBy(entity.getCreatedBy())
                .createdAt(entity.getDateCreated())
                .lastModifiedBy(entity.getUpdatedBy())
                .lastModifiedAt(entity.getDateUpdated())
                .build();
    }



    /**
     * Convert a ProfessionRequest object to a Profession entity.
     *
     * @param request The ProfessionRequest object to convert.
     * @return The converted Profession entity.
     */
    public Profession toEntity(ProfessionRequest request) {
        // Create a new Profession object
        Profession profession = new Profession();

        // Generate a new UUID for the id and trackingId fields
        UUID id = UUID.randomUUID();
        UUID trackingId = UUID.randomUUID();

        // Generate entity data code using the Profession class name
        String codeAuto = entityDataCodeGenerationService.generateEntityDataCode(Profession.class.getSimpleName());

        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Log the generated UUIDs, entity data code, and current date and time
        log.debug("Generating new UUID for id: {}", id);
        log.debug("Generating new UUID for trackingId: {}", trackingId);
        log.debug("Generating entity data code: {}", codeAuto);
        log.debug("Setting dateCreated and dateUpdated to: {}", now);

        // Set the id, trackingId, codeAuto, dateCreated, and dateUpdated fields of the Profession object
        profession.setId(id);
        profession.setTrackingId(trackingId);
        profession.setCodeAuto(codeAuto);
        profession.setDateCreated(now);
        profession.setDateUpdated(now);

        // Copy properties from the request object to the Profession object
        BeanUtils.copyProperties(request, profession);

        // Log the created Profession entity
        log.debug("Profession entity created successfully: {}", profession);

        // Return the created Profession entity
        return profession;
    }

}
