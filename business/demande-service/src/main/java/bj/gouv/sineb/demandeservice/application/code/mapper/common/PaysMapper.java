package bj.gouv.sineb.demandeservice.application.code.mapper.common;

import bj.gouv.sineb.demandeservice.application.code.entity.common.Pays;
import bj.gouv.sineb.demandeservice.application.code.utils.EntityDataCodeGenerationService;
import bj.gouv.sineb.demandeservice.common.dto.request.common.PaysRequest;
import bj.gouv.sineb.demandeservice.common.dto.response.common.PaysResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class PaysMapper {

    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public PaysMapper(EntityDataCodeGenerationService entityDataCodeGenerationService) {
        this.entityDataCodeGenerationService = entityDataCodeGenerationService;
    }

    public PaysResponse toDto(Pays pays) {
        PaysResponse response = new PaysResponse();
        BeanUtils.copyProperties(pays, response);
        return response;
    }

    public Pays toEntity(PaysRequest request) {
        Pays pays = new Pays();
        pays.setTrackingId(UUID.randomUUID());
        pays.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(Pays.class.getSimpleName()));
        pays.setDateCreated(LocalDateTime.now());
        pays.setDateUpdated(LocalDateTime.now());
        BeanUtils.copyProperties(request, pays);
        return pays;
    }

    public Pays toEntity(PaysRequest request, Pays pays) {
        pays.setDateUpdated(LocalDateTime.now());
        BeanUtils.copyProperties(request, pays);
        return pays;
    }
}
