package bj.gouv.sineb.demandeservice.application.code.service.membre;

import bj.gouv.sineb.demandeservice.common.dto.request.membre.DemandeCompteRequest;
import bj.gouv.sineb.demandeservice.common.dto.response.membre.DemandeCompteResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface DemandeCompteService {

    DemandeCompteResponse save(DemandeCompteRequest request);

    DemandeCompteResponse update(DemandeCompteRequest request, UUID trackingId);

    DemandeCompteResponse delete(UUID trackingId);

    DemandeCompteResponse getOne(UUID trackingId);

    Page<DemandeCompteResponse> getAll(int page, int size);
    Page<DemandeCompteResponse> getAllByTypeDemande(UUID typeDemandeId, int page, int size);
    Page<DemandeCompteResponse> getAllByTypeCompte(UUID typeCompteId, int page, int size);
    Page<DemandeCompteResponse> getAllByCivilite(UUID civiliteId, int page, int size);
    Page<DemandeCompteResponse> getAllByPays(UUID paysId, int page, int size);
    boolean emailValid(String email);
    boolean emailPresent(String email);
}
