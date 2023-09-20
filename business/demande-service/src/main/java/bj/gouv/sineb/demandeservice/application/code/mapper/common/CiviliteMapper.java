package bj.gouv.sineb.demandeservice.application.code.mapper.common;

import bj.gouv.sineb.demandeservice.application.code.entity.common.Civilite;
import bj.gouv.sineb.demandeservice.application.code.utils.EntityDataCodeGenerationService;
import bj.gouv.sineb.demandeservice.common.dto.request.common.CiviliteRequest;
import bj.gouv.sineb.demandeservice.common.dto.response.common.CiviliteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CiviliteMapper {

    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public CiviliteResponse toDto(Civilite civilite) {
        CiviliteResponse response = new CiviliteResponse();
        BeanUtils.copyProperties(civilite, response);
        return response;
    }

    public Civilite toEntity(CiviliteRequest request) {
        Civilite civilite = new Civilite();
        civilite.setTrackingId(UUID.randomUUID());
        civilite.setCodeAuto(entityDataCodeGenerationService.generateEntityDataCode(Civilite.class.getSimpleName()));
        civilite.setDateCreated(LocalDateTime.now());
        civilite.setDateUpdated(LocalDateTime.now());
        BeanUtils.copyProperties(request, civilite);
        return civilite;
    }

    public Civilite toEntity(CiviliteRequest request, Civilite civilite) {
        civilite.setDateUpdated(LocalDateTime.now());
        BeanUtils.copyProperties(request, civilite);
        return civilite;
    }

}
