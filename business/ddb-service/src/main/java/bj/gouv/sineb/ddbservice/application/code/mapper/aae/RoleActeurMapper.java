package bj.gouv.sineb.ddbservice.application.code.mapper.aae;

import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.RoleActeurRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.RoleActeurResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.aae.RoleActeur;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RoleActeurMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public RoleActeurMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public RoleActeurResponse toDto(RoleActeur entity){
        return RoleActeurResponse.builder()
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
     * Converts a RoleActeurRequest object to a RoleActeur entity.
     * @param request
     * @return
     */

    public RoleActeur toEntity(RoleActeurRequest request){
        RoleActeur roleActeur = new RoleActeur();
        roleActeur.setId(UUID.randomUUID());
        roleActeur.setTrackingId(UUID.randomUUID());
        roleActeur.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(RoleActeur.class.getSimpleName()));
        BeanUtils.copyProperties(request, roleActeur);
        return roleActeur;
    }

}
