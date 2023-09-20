package bj.gouv.sineb.ddbservice.application.code.service.ppe;



import bj.gouv.sineb.ddbservice.application.code.dto.request.ppe.BeneficiaireCategoryProjetRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.ppe.BeneficiaireCategoryProjetResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface BeneficiaireCategoryProjetService {

    BeneficiaireCategoryProjetResponse save(@Valid BeneficiaireCategoryProjetRequest request);
    BeneficiaireCategoryProjetResponse getOne(UUID id);
    BeneficiaireCategoryProjetResponse getOneByTrackingId(UUID trackingId);
    Page<BeneficiaireCategoryProjetResponse> getAll(int page, int size);
    BeneficiaireCategoryProjetResponse updateOne(@Valid BeneficiaireCategoryProjetRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
