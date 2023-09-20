package bj.gouv.sineb.ddbservice.application.code.service.common;


import bj.gouv.sineb.ddbservice.application.code.dto.request.common.VilleRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.VilleResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface VilleService {

    VilleResponse save(@Valid VilleRequest request);
    VilleResponse getOne(UUID id);
    VilleResponse getOneByTrackingId(UUID trackingId);
    Page<VilleResponse> getAll(int page, int size);
    VilleResponse updateOne(@Valid VilleRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
