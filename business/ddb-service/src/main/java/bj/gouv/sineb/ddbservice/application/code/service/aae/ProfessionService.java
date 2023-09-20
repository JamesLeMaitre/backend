package bj.gouv.sineb.ddbservice.application.code.service.aae;


import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.ProfessionRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.ProfessionResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface ProfessionService {

    ProfessionResponse save(@Valid ProfessionRequest request);
    ProfessionResponse getOne(UUID id);
    ProfessionResponse getOneByTrackingId(UUID trackingId);
    Page<ProfessionResponse> getAll(int page, int size);
    ProfessionResponse updateOne(@Valid ProfessionRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
