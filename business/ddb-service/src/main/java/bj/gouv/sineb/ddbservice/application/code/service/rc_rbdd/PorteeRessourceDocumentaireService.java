package bj.gouv.sineb.ddbservice.application.code.service.rc_rbdd;

import bj.gouv.sineb.ddbservice.application.code.dto.request.rc_rbdd.PorteeRessourceDocumentaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.rc_rbdd.PorteeRessourceDocumentaireResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface PorteeRessourceDocumentaireService {

    PorteeRessourceDocumentaireResponse save(@Valid PorteeRessourceDocumentaireRequest request);
    PorteeRessourceDocumentaireResponse getOne(UUID id);
    PorteeRessourceDocumentaireResponse getOneByTrackingId(UUID trackingId);
    Page<PorteeRessourceDocumentaireResponse> getAll(int page, int size);
    PorteeRessourceDocumentaireResponse updateOne(@Valid PorteeRessourceDocumentaireRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
