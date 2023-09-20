package bj.gouv.sineb.ddbservice.application.code.mapper.sie;


import bj.gouv.sineb.ddbservice.application.code.dto.request.sie.BlocActiviteEconomiqueRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.sie.BlocActiviteEconomiqueResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.ppe.SourceFinancementProjet;
import bj.gouv.sineb.ddbservice.application.code.entity.sie.BlocActiviteEconomique;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BlocActiviteEconomiqueMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public BlocActiviteEconomiqueMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public BlocActiviteEconomiqueResponse toDto(BlocActiviteEconomique entity){
        return BlocActiviteEconomiqueResponse.builder()
                .trackingId(entity.getTrackingId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdBy(entity.getCreatedBy())
                .codeAuto(entity.getCodeAuto())
                .createdAt(entity.getDateCreated())
                .lastModifiedBy(entity.getUpdatedBy())
                .lastModifiedAt(entity.getDateUpdated())
                .build();
    }

    public BlocActiviteEconomique toEntity(BlocActiviteEconomiqueRequest request){
        BlocActiviteEconomique blocActiviteEconomique = new BlocActiviteEconomique();
        blocActiviteEconomique.setId(UUID.randomUUID());
        blocActiviteEconomique.setTrackingId(UUID.randomUUID());
        blocActiviteEconomique.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(BlocActiviteEconomique.class.getSimpleName()));
        blocActiviteEconomique.setName(request.getName());
        blocActiviteEconomique.setDescription(request.getDescription());
        return blocActiviteEconomique;
    }

}
