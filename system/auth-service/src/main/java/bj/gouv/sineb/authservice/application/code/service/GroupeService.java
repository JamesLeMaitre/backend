package bj.gouv.sineb.authservice.application.code.service;

import bj.gouv.sineb.authservice.application.code.dto.request.GroupeRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.GroupeResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface GroupeService {
    GroupeResponse save(@Valid GroupeRequest request);
    GroupeResponse getOne(UUID id);

    GroupeResponse getOneByTrackingId(UUID trackingId);
    Page<GroupeResponse> getAll(int page, int size, boolean deleted);
    GroupeResponse updateOne(@Valid GroupeRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
