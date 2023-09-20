package bj.gouv.sineb.ddbservice.application.code.mapper.ppe;


import bj.gouv.sineb.ddbservice.application.code.dto.request.ppe.BeneficiaireCategoryProjetRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.ppe.BeneficiaireCategoryProjetResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Comite;
import bj.gouv.sineb.ddbservice.application.code.entity.ppe.BeneficiaireCategoryProjet;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BeneficiaireCategoryProjetMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public BeneficiaireCategoryProjetMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public BeneficiaireCategoryProjetResponse toDto(BeneficiaireCategoryProjet entity){
        return BeneficiaireCategoryProjetResponse.builder()
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

    public BeneficiaireCategoryProjet toEntity(BeneficiaireCategoryProjetRequest request){
        BeneficiaireCategoryProjet beneficiaireCategoryProjet = new BeneficiaireCategoryProjet();
        beneficiaireCategoryProjet.setId(UUID.randomUUID());
        beneficiaireCategoryProjet.setTrackingId(UUID.randomUUID());
        beneficiaireCategoryProjet.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(BeneficiaireCategoryProjet.class.getSimpleName()));
        beneficiaireCategoryProjet.setName(request.getName());
        beneficiaireCategoryProjet.setDescription(request.getDescription());
        return beneficiaireCategoryProjet;
    }

}
