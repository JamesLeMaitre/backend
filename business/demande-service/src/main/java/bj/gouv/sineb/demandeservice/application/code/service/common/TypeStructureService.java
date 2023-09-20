package bj.gouv.sineb.demandeservice.application.code.service.common;

import bj.gouv.sineb.demandeservice.common.dto.request.common.TypeStructureRequest;
import bj.gouv.sineb.demandeservice.common.dto.response.common.TypeStructureResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface TypeStructureService {

    TypeStructureResponse save(TypeStructureRequest request);

    TypeStructureResponse update(TypeStructureRequest request, UUID trackingId);

    TypeStructureResponse delete(UUID trackingId);

    TypeStructureResponse getOne(UUID trackingId);

    Page<TypeStructureResponse> getAll(int page, int size);

}
