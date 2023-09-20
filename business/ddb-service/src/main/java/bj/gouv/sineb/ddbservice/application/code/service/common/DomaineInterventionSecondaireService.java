package bj.gouv.sineb.ddbservice.application.code.service.common;

import bj.gouv.sineb.ddbservice.application.code.dto.request.common.DomaineInterventionSecondaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.DomaineInterventionSecondaireResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface DomaineInterventionSecondaireService {

    DomaineInterventionSecondaireResponse save(@Valid DomaineInterventionSecondaireRequest request);
    DomaineInterventionSecondaireResponse getOne(UUID id);
    DomaineInterventionSecondaireResponse getOneByTrackingId(UUID trackingId);
    Page<DomaineInterventionSecondaireResponse> getAll(int page, int size);
    Page<DomaineInterventionSecondaireResponse> getAllByDomaineInterventionPrincipaleId(UUID domaineInterventionPrincipale, int page, int size);
    DomaineInterventionSecondaireResponse updateOne(@Valid DomaineInterventionSecondaireRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
