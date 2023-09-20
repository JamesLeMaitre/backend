package bj.gouv.sineb.ddbservice.application.code.mapper.common;


import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.SousCategoryEltRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.SousCategoryEltResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.CategoryElt;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Etat;
import bj.gouv.sineb.ddbservice.application.code.entity.common.SousCategoryElt;
import bj.gouv.sineb.ddbservice.application.code.repository.common.CategoryEltRepository;
import bj.gouv.sineb.ddbservice.application.code.utils.EntityDataCodeGenerationService;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class SousCategoryEltMapper {
    private final EntityDataCodeGenerationService entityDataCodeGenerationService;
    private final CategoryEltMapper categoryEltMapper;
    private final CategoryEltRepository categoryEltRepository;

    public SousCategoryEltMapper(EntityDataCodeGenerationService entityDataCodeGenerationService,
                                 CategoryEltMapper categoryEltMapper,
                                 CategoryEltRepository categoryEltRepository) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
        this.categoryEltMapper = categoryEltMapper;
        this.categoryEltRepository = categoryEltRepository;
    }



    public SousCategoryEltResponse toDto(SousCategoryElt entity){
        return SousCategoryEltResponse.builder()
                .trackingId(entity.getTrackingId())
                .name(entity.getName())
                .description(entity.getDescription())
                .categorieEltResponse(categoryEltMapper.toDto(entity.getCategoryElt()))
                .createdBy(entity.getCreatedBy())
                .createdAt(entity.getDateCreated())
                .codeAuto(entity.getCodeAuto())
                .lastModifiedBy(entity.getUpdatedBy())
                .lastModifiedAt(entity.getDateUpdated())
                .build();
    }

    public SousCategoryElt toEntity(SousCategoryEltRequest request){
        Optional<CategoryElt> categoryEltOptional = categoryEltRepository
                .findByTrackingId(request.getCategoryEltTrackingId());
        if (categoryEltOptional.isEmpty())
            throw new CustomNotFoundException("CategoryElt with trackingId: "+request.getCategoryEltTrackingId()+" not found !");

        SousCategoryElt sousCategoryElt = new SousCategoryElt();
        sousCategoryElt.setId(UUID.randomUUID());
        sousCategoryElt.setTrackingId(UUID.randomUUID());
        sousCategoryElt.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(SousCategoryElt.class.getSimpleName()));
        sousCategoryElt.setName(request.getName());
        sousCategoryElt.setDescription(request.getDescription());
        return sousCategoryElt;
    }

}
