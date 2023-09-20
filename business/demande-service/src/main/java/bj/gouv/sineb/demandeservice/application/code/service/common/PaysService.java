package bj.gouv.sineb.demandeservice.application.code.service.common;

import bj.gouv.sineb.demandeservice.common.dto.request.common.PaysRequest;
import bj.gouv.sineb.demandeservice.common.dto.response.common.PaysResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface PaysService {

    PaysResponse save(PaysRequest request);

    PaysResponse update(PaysRequest request, UUID trackingId);

    PaysResponse delete(UUID trackingId);

    PaysResponse getOne(UUID trackingId);

    Page<PaysResponse> getAll(int page, int size);

}
