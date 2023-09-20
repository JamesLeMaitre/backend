package bj.gouv.sineb.ddbservice.application.code.mapper.sie;


import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.ddbservice.application.code.dto.request.sie.BrancheActiviteEconomiqueRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.sie.BrancheActiviteEconomiqueResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.sie.BlocActiviteEconomique;
import bj.gouv.sineb.ddbservice.application.code.entity.sie.BrancheActiviteEconomique;
import bj.gouv.sineb.ddbservice.application.code.repository.sie.BlocActiviteEconomiqueRepository;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class BrancheActiviteEconomiqueMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;
    private final BlocActiviteEconomiqueMapper blocActiviteEconomiqueMapper;
    private final BlocActiviteEconomiqueRepository blocActiviteEconomiqueRepository;

    public BrancheActiviteEconomiqueMapper(EntityDataCodeGenerationService entityDataCodeGenerationService, BlocActiviteEconomiqueMapper blocActiviteEconomiqueMapper, BlocActiviteEconomiqueRepository blocActiviteEconomiqueRepository) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
        this.blocActiviteEconomiqueMapper = blocActiviteEconomiqueMapper;
        this.blocActiviteEconomiqueRepository = blocActiviteEconomiqueRepository;
    }

    public BrancheActiviteEconomiqueResponse toDto(BrancheActiviteEconomique entity){
        return BrancheActiviteEconomiqueResponse.builder()
                .trackingId(entity.getTrackingId())
                .name(entity.getName())
                .description(entity.getDescription())
                .blocActiviteEconomiqueResponse(blocActiviteEconomiqueMapper.toDto(entity.getBlocActiviteEconomique()))
                .createdBy(entity.getCreatedBy())
                .codeAuto(entity.getCodeAuto())
                .createdAt(entity.getDateCreated())
                .lastModifiedBy(entity.getUpdatedBy())
                .lastModifiedAt(entity.getDateUpdated())
                .build();
    }

    public BrancheActiviteEconomique toEntity(BrancheActiviteEconomiqueRequest request){
        Optional<BlocActiviteEconomique> blocActiviteEconomiqueOptional = blocActiviteEconomiqueRepository
                .findByTrackingId(request.getBlocActiviteEconomiqueTrackingId());
        if (blocActiviteEconomiqueOptional.isEmpty())
            throw new CustomNotFoundException("BlocActiviteEconomique with trackingId: "+request.getBlocActiviteEconomiqueTrackingId()+" not found !");

        BrancheActiviteEconomique brancheActiviteEconomique = new BrancheActiviteEconomique();
        brancheActiviteEconomique.setId(UUID.randomUUID());
        brancheActiviteEconomique.setTrackingId(UUID.randomUUID());
        brancheActiviteEconomique.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(BrancheActiviteEconomique.class.getSimpleName()));
        brancheActiviteEconomique.setName(request.getName());
        brancheActiviteEconomique.setDescription(request.getDescription());
        return brancheActiviteEconomique;

    }
}
