package bj.gouv.sineb.ddbservice.application.code.service.common;


import bj.gouv.sineb.ddbservice.application.code.dto.request.common.ModeReceptionRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.ModeReceptionResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface ModeReceptionService {

    ModeReceptionResponse save(@Valid ModeReceptionRequest request);
    ModeReceptionResponse getOne(UUID id);
    ModeReceptionResponse getOneByTrackingId(UUID trackingId);
    Page<ModeReceptionResponse> getAll(int page, int size);
    ModeReceptionResponse updateOne(@Valid ModeReceptionRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
