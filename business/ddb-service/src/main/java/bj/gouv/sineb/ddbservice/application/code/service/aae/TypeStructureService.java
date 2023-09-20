package bj.gouv.sineb.ddbservice.application.code.service.aae;

import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.TypeStructureRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.TypeStructureResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface TypeStructureService {

    TypeStructureResponse save(TypeStructureRequest request);

    TypeStructureResponse update(TypeStructureRequest request, UUID trackingId);

    TypeStructureResponse delete(UUID trackingId);

    TypeStructureResponse getOne(UUID trackingId);

    Page<TypeStructureResponse> getAll(int page, int size);

    long count();

}
