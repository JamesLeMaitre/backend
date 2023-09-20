package bj.gouv.sineb.ddbservice.application.code.service.aae;

import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.FormeJuridiqueActeurRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.FormeJuridiqueActeurResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;


@Validated
public interface FormeJuridiqueActeurService {

    FormeJuridiqueActeurResponse save(@Valid FormeJuridiqueActeurRequest request);
    FormeJuridiqueActeurResponse getOne(UUID id);
    FormeJuridiqueActeurResponse getOneByTrackingId(UUID trackingId);
    Page<FormeJuridiqueActeurResponse> getAll(int page, int size);
    FormeJuridiqueActeurResponse updateOne(@Valid FormeJuridiqueActeurRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
