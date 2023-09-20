package bj.gouv.sineb.authservice.application.code.service;


import bj.gouv.sineb.authservice.application.code.dto.request.SystemVariableRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.SystemVariableResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface SystemVariableService {
    SystemVariableResponse save(@Valid SystemVariableRequest request);
    SystemVariableResponse getOne(UUID id);

    SystemVariableResponse getOneByTrackingId(UUID trackingId);
    Page<SystemVariableResponse> getAll(int page, int size, boolean deleted);
    SystemVariableResponse updateOne(@Valid SystemVariableRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
