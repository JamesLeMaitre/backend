package bj.gouv.sineb.authservice.application.code.service;

import bj.gouv.sineb.authservice.application.code.dto.request.UserRoleRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.UserRoleResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface UserRoleService {
    UserRoleResponse save(@Valid UserRoleRequest request);
    UserRoleResponse getOne(UUID id);

    UserRoleResponse getOneByTrackingId(UUID trackingId);
    Page<UserRoleResponse> getAll(int page, int size, boolean deleted);
    UserRoleResponse updateOne(@Valid UserRoleRequest request);
    void deleteOne(UUID trackingId);
    long count();
    Page<UserRoleResponse> getAllByRoleId(UUID roleId, int page, int size, boolean deleted);
}
