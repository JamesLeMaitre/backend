package bj.gouv.sineb.ddbservice.application.code.service.rc_rbdd;

import bj.gouv.sineb.ddbservice.application.code.dto.request.rc_rbdd.FormatRessourceDocumentaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.rc_rbdd.FormatRessourceDocumentaireResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface FormatRessourceDocumentaireService {

    FormatRessourceDocumentaireResponse save(@Valid FormatRessourceDocumentaireRequest request);
    FormatRessourceDocumentaireResponse getOne(UUID id);
    FormatRessourceDocumentaireResponse getOneByTrackingId(UUID trackingId);
    Page<FormatRessourceDocumentaireResponse> getAll(int page, int size);
    FormatRessourceDocumentaireResponse updateOne(@Valid FormatRessourceDocumentaireRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
