package bj.gouv.sineb.authservice.application.code.service;


import bj.gouv.sineb.authservice.application.code.dto.request.RessourceRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.RessourceResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface RessourceService {
    RessourceResponse save(@Valid RessourceRequest request);
    RessourceResponse getOne(UUID id);

    RessourceResponse getOneByTrackingId(UUID trackingId);
    Page<RessourceResponse> getAll(int page, int size, boolean deleted);
    RessourceResponse updateOne(@Valid RessourceRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
