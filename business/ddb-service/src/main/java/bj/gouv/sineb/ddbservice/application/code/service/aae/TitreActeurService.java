package bj.gouv.sineb.ddbservice.application.code.service.aae;


import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.TitreActeurRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.TitreActeurResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface TitreActeurService {

    TitreActeurResponse save(@Valid TitreActeurRequest request);
    TitreActeurResponse getOne(UUID id);
    TitreActeurResponse getOneByTrackingId(UUID trackingId);
    Page<TitreActeurResponse> getAll(int page, int size);
    TitreActeurResponse updateOne(@Valid TitreActeurRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
