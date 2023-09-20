package bj.gouv.sineb.ddbservice.application.code.service.rc_rbdd;


import bj.gouv.sineb.ddbservice.application.code.dto.request.rc_rbdd.DroitAccesRessourceDocumentaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.rc_rbdd.DroitAccesRessourceDocumentaireResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface DroitAccesRessourceDocumentaireService {

    DroitAccesRessourceDocumentaireResponse save(@Valid DroitAccesRessourceDocumentaireRequest request);
    DroitAccesRessourceDocumentaireResponse getOne(UUID id);
    DroitAccesRessourceDocumentaireResponse getOneByTrackingId(UUID trackingId);
    Page<DroitAccesRessourceDocumentaireResponse> getAll(int page, int size);
    DroitAccesRessourceDocumentaireResponse updateOne(@Valid DroitAccesRessourceDocumentaireRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
