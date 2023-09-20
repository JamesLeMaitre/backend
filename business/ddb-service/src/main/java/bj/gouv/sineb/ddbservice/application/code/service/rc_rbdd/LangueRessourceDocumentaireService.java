package bj.gouv.sineb.ddbservice.application.code.service.rc_rbdd;


import bj.gouv.sineb.ddbservice.application.code.dto.request.rc_rbdd.LangueRessourceDocumentaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.rc_rbdd.LangueRessourceDocumentaireResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface LangueRessourceDocumentaireService {

    LangueRessourceDocumentaireResponse save(@Valid LangueRessourceDocumentaireRequest request);
    LangueRessourceDocumentaireResponse getOne(UUID id);
    LangueRessourceDocumentaireResponse getOneByTrackingId(UUID trackingId);
    Page<LangueRessourceDocumentaireResponse> getAll(int page, int size);
    LangueRessourceDocumentaireResponse updateOne(@Valid LangueRessourceDocumentaireRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
