package bj.gouv.sineb.authservice.application.code.service;

import bj.gouv.sineb.authservice.application.code.dto.request.PolicieRequest;
import bj.gouv.sineb.authservice.application.code.dto.request.PolicieUpdateRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.PolicieResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface PolicieService {
    void save(@Valid PolicieRequest dto);
    PolicieResponse getOne(UUID id);

    PolicieResponse getOneByTrackingId(UUID trackingId);
    Page<PolicieResponse> getAll(int page, int size, boolean deleted, boolean expired);
    PolicieResponse updateOne(@Valid PolicieUpdateRequest request);
    void deleteOne(UUID userTrackingId, UUID ressourceTrackingId, UUID scopeTrackingId);
    long count();
    void revokeExpiredPolicies();
}
