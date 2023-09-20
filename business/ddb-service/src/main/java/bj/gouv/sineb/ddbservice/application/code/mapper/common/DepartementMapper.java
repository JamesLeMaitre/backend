package bj.gouv.sineb.ddbservice.application.code.mapper.common;

import bj.gouv.sineb.ddbservice.application.code.dto.request.common.DepartementRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.DepartementResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Commune;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Departement;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DepartementMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public DepartementMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public DepartementResponse toDto(Departement entity){
        return DepartementResponse.builder()
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

    public Departement toEntity(DepartementRequest request){
        Departement departement = new Departement();
        departement.setId(UUID.randomUUID());
        departement.setTrackingId(UUID.randomUUID());
        departement.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(Departement.class.getSimpleName()));
        departement.setName(request.getName());
        departement.setDescription(request.getDescription());
        return departement;
    }

}
