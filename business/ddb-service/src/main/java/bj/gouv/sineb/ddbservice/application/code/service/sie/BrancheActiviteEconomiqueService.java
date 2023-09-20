package bj.gouv.sineb.ddbservice.application.code.service.sie;


import bj.gouv.sineb.ddbservice.application.code.dto.request.sie.BrancheActiviteEconomiqueRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.sie.BrancheActiviteEconomiqueResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface BrancheActiviteEconomiqueService {

    BrancheActiviteEconomiqueResponse save(@Valid BrancheActiviteEconomiqueRequest request);
    BrancheActiviteEconomiqueResponse getOne(UUID id);
    BrancheActiviteEconomiqueResponse getOneByTrackingId(UUID trackingId);
    Page<BrancheActiviteEconomiqueResponse> getAll(int page, int size);
    Page<BrancheActiviteEconomiqueResponse> getAllByBlocActiviteEconomiqueId(UUID flocActiviteEconomiqueTrackingId, int page, int size);
    BrancheActiviteEconomiqueResponse updateOne(@Valid BrancheActiviteEconomiqueRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
