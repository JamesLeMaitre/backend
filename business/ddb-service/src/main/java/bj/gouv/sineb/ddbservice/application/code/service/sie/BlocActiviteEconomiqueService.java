package bj.gouv.sineb.ddbservice.application.code.service.sie;

import bj.gouv.sineb.ddbservice.application.code.dto.request.sie.BlocActiviteEconomiqueRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.sie.BlocActiviteEconomiqueResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface BlocActiviteEconomiqueService {

    BlocActiviteEconomiqueResponse save(@Valid BlocActiviteEconomiqueRequest request);
    BlocActiviteEconomiqueResponse getOne(UUID id);
    BlocActiviteEconomiqueResponse getOneByTrackingId(UUID trackingId);
    Page<BlocActiviteEconomiqueResponse> getAll(int page, int size);
    BlocActiviteEconomiqueResponse updateOne(@Valid BlocActiviteEconomiqueRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
