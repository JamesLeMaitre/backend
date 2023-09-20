package bj.gouv.sineb.ddbservice.application.code.mapper.ppe;


import bj.gouv.sineb.ddbservice.application.code.dto.request.ppe.SourceFinancementProjetRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.ppe.SourceFinancementProjetResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.ppe.BeneficiaireCategoryProjet;
import bj.gouv.sineb.ddbservice.application.code.entity.ppe.SourceFinancementProjet;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SourceFinancementProjetMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public SourceFinancementProjetMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public SourceFinancementProjetResponse toDto(SourceFinancementProjet entity){
        return SourceFinancementProjetResponse.builder()
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

    public SourceFinancementProjet toEntity(SourceFinancementProjetRequest request){
        SourceFinancementProjet sourceFinancementProjet = new SourceFinancementProjet();
        sourceFinancementProjet.setId(UUID.randomUUID());
        sourceFinancementProjet.setTrackingId(UUID.randomUUID());
        sourceFinancementProjet.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(SourceFinancementProjet.class.getSimpleName()));
        sourceFinancementProjet.setName(request.getName());
        sourceFinancementProjet.setDescription(request.getDescription());
        return sourceFinancementProjet;
    }

}
