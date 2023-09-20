package bj.gouv.sineb.demandeservice.application.code.service.common;

import bj.gouv.sineb.demandeservice.common.dto.request.common.CiviliteRequest;
import bj.gouv.sineb.demandeservice.common.dto.response.common.CiviliteResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface CiviliteService {

    CiviliteResponse save(CiviliteRequest request);

    CiviliteResponse update(CiviliteRequest request, UUID trackingId);

    CiviliteResponse delete(UUID trackingId);

    CiviliteResponse getOne(UUID trackingId);

    Page<CiviliteResponse> getAll(int page, int size);

}
