package bj.gouv.sineb.ddbservice.application.code.service.common;

import bj.gouv.sineb.ddbservice.application.code.dto.request.common.EtatRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.EtatResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface EtatService {

    EtatResponse save(@Valid EtatRequest request);
    EtatResponse getOne(UUID id);
    EtatResponse getOneByTrackingId(UUID trackingId);
    Page<EtatResponse> getAll(int page, int size);
    EtatResponse updateOne(@Valid EtatRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
