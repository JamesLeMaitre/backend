package bj.gouv.sineb.ddbservice.application.code.service.common;


import bj.gouv.sineb.ddbservice.application.code.dto.request.common.CategoryEltRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.CategoryEltResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface CategoryEltService {

    CategoryEltResponse save(@Valid CategoryEltRequest request);
    CategoryEltResponse getOne(UUID id);
    CategoryEltResponse getOneByTrackingId(UUID trackingId);
    Page<CategoryEltResponse> getAll(int page, int size);
    CategoryEltResponse updateOne(@Valid CategoryEltRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
