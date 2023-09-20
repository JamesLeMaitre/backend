package bj.gouv.sineb.ddbservice.application.code.service.common;


import bj.gouv.sineb.ddbservice.application.code.dto.request.common.DomaineInterventionPrincipaleRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.DomaineInterventionPrincipaleResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface DomaineInterventionPrincipaleService {

    DomaineInterventionPrincipaleResponse save(@Valid DomaineInterventionPrincipaleRequest request);
    DomaineInterventionPrincipaleResponse getOne(UUID id);
    DomaineInterventionPrincipaleResponse getOneByTrackingId(UUID trackingId);
    Page<DomaineInterventionPrincipaleResponse> getAll(int page, int size);
    DomaineInterventionPrincipaleResponse updateOne(@Valid DomaineInterventionPrincipaleRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
