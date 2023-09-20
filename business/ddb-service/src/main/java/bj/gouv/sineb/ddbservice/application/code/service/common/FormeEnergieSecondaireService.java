package bj.gouv.sineb.ddbservice.application.code.service.common;

import bj.gouv.sineb.ddbservice.application.code.dto.request.common.FormeEnergieSecondaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.FormeEnergieSecondaireResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface FormeEnergieSecondaireService {

    FormeEnergieSecondaireResponse save(@Valid FormeEnergieSecondaireRequest request);
    FormeEnergieSecondaireResponse getOne(UUID id);
    FormeEnergieSecondaireResponse getOneByTrackingId(UUID trackingId);
    Page<FormeEnergieSecondaireResponse> getAll(int page, int size);
    Page<FormeEnergieSecondaireResponse> getAllByFormeEnergiePrimaireId(UUID formeEnergiePrimaireTrackingId, int page, int size);
    FormeEnergieSecondaireResponse updateOne(@Valid FormeEnergieSecondaireRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
