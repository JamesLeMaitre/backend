package bj.gouv.sineb.ddbservice.application.code.service.common;


import bj.gouv.sineb.ddbservice.application.code.dto.request.common.ComiteRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.ComiteResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface ComiteService {

    ComiteResponse save(@Valid ComiteRequest request);
    ComiteResponse getOne(UUID id);
    ComiteResponse getOneByTrackingId(UUID trackingId);
    Page<ComiteResponse> getAll(int page, int size);
    ComiteResponse updateOne(@Valid ComiteRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
