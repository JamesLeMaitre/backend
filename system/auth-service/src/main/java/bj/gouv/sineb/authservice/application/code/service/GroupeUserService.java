package bj.gouv.sineb.authservice.application.code.service;

import bj.gouv.sineb.authservice.application.code.dto.request.GroupeUserRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.GroupeUserResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface GroupeUserService {
    GroupeUserResponse save(@Valid GroupeUserRequest request);
    GroupeUserResponse getOne(UUID id);
    GroupeUserResponse getOneByTrackingId(UUID trackingId);
    Page<GroupeUserResponse> getAll(int page, int size, boolean deleted);
    GroupeUserResponse updateOne(@Valid GroupeUserRequest request);
    void deleteOne(UUID trackingId);
    long count();
    Page<GroupeUserResponse> getAllByUserId(UUID userId, int page, int size, boolean deleted);
}
