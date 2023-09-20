package bj.gouv.sineb.ddbservice.application.code.service.common;

import bj.gouv.sineb.ddbservice.application.code.dto.request.common.DepartementRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.DepartementResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface DepartementService {

    DepartementResponse save(@Valid DepartementRequest request);
    DepartementResponse getOne(UUID id);
    DepartementResponse getOneByTrackingId(UUID trackingId);
    Page<DepartementResponse> getAll(int page, int size);
    DepartementResponse updateOne(@Valid DepartementRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
