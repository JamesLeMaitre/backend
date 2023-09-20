package bj.gouv.sineb.ddbservice.application.code.mapper.common;


import bj.gouv.sineb.ddbservice.application.code.dto.request.common.VilleRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.VilleResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Commune;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Ville;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VilleMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public VilleMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public VilleResponse toDto(Ville entity){
        return VilleResponse.builder()
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

    public Ville toEntity(VilleRequest request){
        Ville ville = new Ville();
        ville.setId(UUID.randomUUID());
        ville.setTrackingId(UUID.randomUUID());
        ville.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(Ville.class.getSimpleName()));
        ville.setName(request.getName());
        ville.setDescription(request.getDescription());
        return ville;
    }

}
