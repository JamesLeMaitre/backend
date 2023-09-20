package bj.gouv.sineb.ddbservice.application.code.mapper.common;


import bj.gouv.sineb.ddbservice.application.code.dto.request.common.CategoryEltRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.CategoryEltResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.CategoryElt;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CategoryEltMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public CategoryEltMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public CategoryEltResponse toDto(CategoryElt entity){
        return CategoryEltResponse.builder()
                .trackingId(entity.getTrackingId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdBy(entity.getCreatedBy())
                .createdAt(entity.getDateCreated())
                .codeAuto(entity.getCodeAuto())
                .lastModifiedBy(entity.getUpdatedBy())
                .lastModifiedAt(entity.getDateUpdated())
                .build();
    }

    /**
     *  Convert a CategoryEltRequest object to a CategoryElt entity.
     * @param request
     * @return
     */

    /**
     * Converts a CategoryEltRequest object to a CategoryElt object.
     *
     * @param request the CategoryEltRequest object containing the data to convert
     * @return the converted CategoryElt object
     */
    public CategoryElt toEntity(CategoryEltRequest request) {
        // Create a new CategoryElt object
        CategoryElt categoryElt = new CategoryElt();

        // Generate a new random UUID for the id property
        categoryElt.setId(UUID.randomUUID());

        // Generate a new random UUID for the trackingId property
        categoryElt.setTrackingId(UUID.randomUUID());

        // Generate a new code using the entityDataCodeGenerationService
        // for the codeAuto property
        categoryElt.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(CategoryElt.class.getSimpleName()));

        // Set the name property from the request object
        categoryElt.setName(request.getName());

        // Set the description property from the request object
        categoryElt.setDescription(request.getDescription());

        // Return the converted CategoryElt object
        return categoryElt;
    }

}
