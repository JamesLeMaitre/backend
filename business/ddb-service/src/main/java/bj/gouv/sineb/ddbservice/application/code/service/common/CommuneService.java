package bj.gouv.sineb.ddbservice.application.code.service.common;


import bj.gouv.sineb.ddbservice.application.code.dto.request.common.CommuneRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.CommuneResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface CommuneService {

    CommuneResponse save(@Valid CommuneRequest request);
    CommuneResponse getOne(UUID id);
    CommuneResponse getOneByTrackingId(UUID trackingId);
    Page<CommuneResponse> getAll(int page, int size);
    CommuneResponse updateOne(@Valid CommuneRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
