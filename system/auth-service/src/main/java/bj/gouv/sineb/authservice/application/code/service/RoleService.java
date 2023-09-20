package bj.gouv.sineb.authservice.application.code.service;


import bj.gouv.sineb.authservice.application.code.dto.request.RoleRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.RoleResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface RoleService {
    RoleResponse save(@Valid RoleRequest request);
    RoleResponse getOne(UUID id);

    RoleResponse getOneByTrackingId(UUID trackingId);
    Page<RoleResponse> getAll(int page, int size, boolean deleted);
    RoleResponse updateOne(@Valid RoleRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
