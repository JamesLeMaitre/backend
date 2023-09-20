package bj.gouv.sineb.ddbservice.application.code.service.ppe;

import bj.gouv.sineb.ddbservice.application.code.dto.request.ppe.SourceFinancementProjetRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.ppe.SourceFinancementProjetResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface SourceFinancementProjetService {

    SourceFinancementProjetResponse save(@Valid SourceFinancementProjetRequest request);
    SourceFinancementProjetResponse getOne(UUID id);
    SourceFinancementProjetResponse getOneByTrackingId(UUID trackingId);
    Page<SourceFinancementProjetResponse> getAll(int page, int size);
    SourceFinancementProjetResponse updateOne(@Valid SourceFinancementProjetRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
