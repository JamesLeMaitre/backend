package bj.gouv.sineb.ddbservice.application.code.mapper.aae;


import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.MotifQualificationRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.MotifQualificationResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.aae.MotifQualification;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MotifQualificationMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public MotifQualificationMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public MotifQualificationResponse toDto(MotifQualification entity){
        return MotifQualificationResponse.builder()
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
     * Convert a MotifQualificationRequest to a MotifQualification entity.
     *
     * @param request the MotifQualificationRequest object
     * @return the MotifQualification entity
     */
    public MotifQualification toEntity(MotifQualificationRequest request) {
        // Generate a random UUID for the id and trackingId
        UUID id = UUID.randomUUID();
        UUID trackingId = UUID.randomUUID();

        // Create a new MotifQualification entity
        MotifQualification entity = new MotifQualification();

        // Set the id, trackingId, and code using the generated UUIDs and entityDataCodeGenerationService
        entity.setId(id);
        entity.setTrackingId(trackingId);
        entity.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(MotifQualification.class.getSimpleName()));

        // Set the name and description from the request object
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());

        // Return the created MotifQualification entity
        return entity;
    }

}
