package bj.gouv.sineb.ddbservice.application.code.service.aae;


import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.CiviliteRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.CiviliteResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;


@Validated
public interface CiviliteService {

    CiviliteResponse save(@Valid CiviliteRequest request);
    CiviliteResponse getOne(UUID id);
    CiviliteResponse getOneByTrackingId(UUID trackingId);
    Page<CiviliteResponse> getAll(int page, int size);
    CiviliteResponse updateOne(@Valid CiviliteRequest request, UUID trackingId);
    CiviliteResponse deleteOne(UUID trackingId);
    long count();
}
