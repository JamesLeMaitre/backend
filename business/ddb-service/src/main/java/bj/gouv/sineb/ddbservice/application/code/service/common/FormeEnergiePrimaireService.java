package bj.gouv.sineb.ddbservice.application.code.service.common;

import bj.gouv.sineb.ddbservice.application.code.dto.request.common.FormeEnergiePrimaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.FormeEnergiePrimaireResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface FormeEnergiePrimaireService {

    FormeEnergiePrimaireResponse save(@Valid FormeEnergiePrimaireRequest request);
    FormeEnergiePrimaireResponse getOne(UUID id);
    FormeEnergiePrimaireResponse getOneByTrackingId(UUID trackingId);
    Page<FormeEnergiePrimaireResponse> getAll(int page, int size);
    FormeEnergiePrimaireResponse updateOne(@Valid FormeEnergiePrimaireRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
