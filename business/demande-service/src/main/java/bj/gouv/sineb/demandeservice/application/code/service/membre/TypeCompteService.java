package bj.gouv.sineb.demandeservice.application.code.service.membre;

import bj.gouv.sineb.demandeservice.common.dto.request.membre.TypeCompteRequest;
import bj.gouv.sineb.demandeservice.common.dto.response.membre.TypeCompteResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface TypeCompteService {

    TypeCompteResponse save(TypeCompteRequest request);

    TypeCompteResponse update(TypeCompteRequest request, UUID trackingId);

    TypeCompteResponse delete(UUID trackingId);

    TypeCompteResponse getOne(UUID trackingId);

    Page<TypeCompteResponse> getAll(int page, int size);

}
