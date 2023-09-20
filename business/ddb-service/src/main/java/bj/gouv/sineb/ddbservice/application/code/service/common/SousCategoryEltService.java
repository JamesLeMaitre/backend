package bj.gouv.sineb.ddbservice.application.code.service.common;

import bj.gouv.sineb.ddbservice.application.code.dto.request.common.SousCategoryEltRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.SousCategoryEltResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface SousCategoryEltService {

    SousCategoryEltResponse save(@Valid SousCategoryEltRequest request);
    SousCategoryEltResponse getOne(UUID id);
    SousCategoryEltResponse getOneByTrackingId(UUID trackingId);
    Page<SousCategoryEltResponse> getAll(int page, int size);
    Page<SousCategoryEltResponse> getAllByCategoryId(UUID categoryTrackingId, int page, int size);
    SousCategoryEltResponse updateOne(@Valid SousCategoryEltRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
