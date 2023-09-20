package bj.gouv.sineb.demandeservice.application.code.service.common;

import bj.gouv.sineb.demandeservice.common.dto.request.common.TypeDemandeRequest;
import bj.gouv.sineb.demandeservice.common.dto.response.common.TypeDemandeResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface TypeDemandeService {

    TypeDemandeResponse save(TypeDemandeRequest request);

    TypeDemandeResponse update(TypeDemandeRequest request, UUID trackingId);

    TypeDemandeResponse delete(UUID trackingId);

    TypeDemandeResponse getOne(UUID trackingId);

    Page<TypeDemandeResponse> getAll(int page, int size);

}
