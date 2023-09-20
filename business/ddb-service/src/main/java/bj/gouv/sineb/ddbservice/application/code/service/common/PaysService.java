package bj.gouv.sineb.ddbservice.application.code.service.common;

import bj.gouv.sineb.ddbservice.application.code.dto.request.common.PaysRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.PaysResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface PaysService {

    PaysResponse save(@Valid PaysRequest request);
    PaysResponse getOne(UUID id);
    PaysResponse getOneByTrackingId(UUID trackingId);
    Page<PaysResponse> getAll(int page, int size);
    PaysResponse updateOne(@Valid PaysRequest request, UUID trackingId);
    PaysResponse deleteOne(UUID trackingId);
    long count();
}
