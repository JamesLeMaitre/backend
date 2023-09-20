package bj.gouv.sineb.ddbservice.application.code.service.common;

import bj.gouv.sineb.ddbservice.application.code.dto.request.common.ArrondissementRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.ArrondissementResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface ArrondissementService {

    ArrondissementResponse save(@Valid ArrondissementRequest request);
    ArrondissementResponse getOne(UUID id);
    ArrondissementResponse getOneByTrackingId(UUID trackingId);
    Page<ArrondissementResponse> getAll(int page, int size);
    ArrondissementResponse updateOne(@Valid ArrondissementRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
