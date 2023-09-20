package bj.gouv.sineb.authservice.application.code.service;

import bj.gouv.sineb.authservice.application.code.dto.request.ScopeRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.ScopeResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface ScopeService {
    ScopeResponse save(@Valid ScopeRequest request);
    ScopeResponse getOne(UUID id);

    ScopeResponse getOneByTrackingId(UUID trackingId);
    Page<ScopeResponse> getAll(int page, int size, boolean deleted);
    ScopeResponse updateOne(@Valid ScopeRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
