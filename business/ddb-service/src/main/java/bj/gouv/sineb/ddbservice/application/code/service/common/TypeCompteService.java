package bj.gouv.sineb.ddbservice.application.code.service.common;

import bj.gouv.sineb.ddbservice.application.code.dto.request.common.TypeCompteRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.TypeCompteResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface TypeCompteService {

    TypeCompteResponse save(TypeCompteRequest request);

    TypeCompteResponse update(TypeCompteRequest request, UUID trackingId);

    TypeCompteResponse delete(UUID trackingId);

    TypeCompteResponse getOne(UUID trackingId);

    Page<TypeCompteResponse> getAll(int page, int size);

    long count();

}
