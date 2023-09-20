package bj.gouv.sineb.authservice.application.code.service;


import bj.gouv.sineb.authservice.application.code.dto.request.AppEventRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.AppEventResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface AppEventService {
    AppEventResponse save(@Valid AppEventRequest request);
    AppEventResponse getOne(UUID id);
    AppEventResponse getOneByTrackingId(UUID trackingId);
    Page<AppEventResponse> getAll(int page, int size, boolean deleted);
    AppEventResponse updateOne(@Valid AppEventRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
