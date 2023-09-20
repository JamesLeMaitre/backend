package bj.gouv.sineb.ddbservice.application.code.service.rc_rbdd;

import bj.gouv.sineb.ddbservice.application.code.dto.request.rc_rbdd.TypeRessourceDocumentaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.rc_rbdd.TypeRessourceDocumentaireResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface TypeRessourceDocumentaireService {

    TypeRessourceDocumentaireResponse save(@Valid TypeRessourceDocumentaireRequest request);
    TypeRessourceDocumentaireResponse getOne(UUID id);
    TypeRessourceDocumentaireResponse getOneByTrackingId(UUID trackingId);
    Page<TypeRessourceDocumentaireResponse> getAll(int page, int size);
    TypeRessourceDocumentaireResponse updateOne(@Valid TypeRessourceDocumentaireRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
