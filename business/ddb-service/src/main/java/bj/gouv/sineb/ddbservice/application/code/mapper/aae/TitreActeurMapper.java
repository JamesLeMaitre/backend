package bj.gouv.sineb.ddbservice.application.code.mapper.aae;

import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.TitreActeurRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.TitreActeurResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.aae.Civilite;
import bj.gouv.sineb.ddbservice.application.code.entity.aae.TitreActeur;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TitreActeurMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public TitreActeurMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public TitreActeurResponse toDto(TitreActeur entity){
        return TitreActeurResponse.builder()
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

    public TitreActeur toEntity(TitreActeurRequest request){
        TitreActeur titreActeur = new TitreActeur();
        titreActeur.setId(UUID.randomUUID());
        titreActeur.setTrackingId(UUID.randomUUID());
        titreActeur.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(Civilite.class.getSimpleName()));
        BeanUtils.copyProperties(request, titreActeur);
        return titreActeur;
    }

}
