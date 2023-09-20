package bj.gouv.sineb.ddbservice.application.code.service.aae;


import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.MotifQualificationRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.MotifQualificationResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface MotifQualificationService {

    MotifQualificationResponse save(@Valid MotifQualificationRequest request);
    MotifQualificationResponse getOne(UUID id);
    MotifQualificationResponse getOneByTrackingId(UUID trackingId);
    Page<MotifQualificationResponse> getAll(int page, int size);
    MotifQualificationResponse updateOne(@Valid MotifQualificationRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
