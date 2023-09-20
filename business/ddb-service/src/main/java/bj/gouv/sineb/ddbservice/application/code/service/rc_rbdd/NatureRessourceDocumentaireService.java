package bj.gouv.sineb.ddbservice.application.code.service.rc_rbdd;

import bj.gouv.sineb.ddbservice.application.code.dto.request.rc_rbdd.NatureRessourceDocumentaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.rc_rbdd.NatureRessourceDocumentaireResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface NatureRessourceDocumentaireService {

    NatureRessourceDocumentaireResponse save(@Valid NatureRessourceDocumentaireRequest request);
    NatureRessourceDocumentaireResponse getOne(UUID id);
    NatureRessourceDocumentaireResponse getOneByTrackingId(UUID trackingId);
    Page<NatureRessourceDocumentaireResponse> getAll(int page, int size);
    NatureRessourceDocumentaireResponse updateOne(@Valid NatureRessourceDocumentaireRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
