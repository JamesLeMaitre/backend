package bj.gouv.sineb.ddbservice.application.code.service.aae;



import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.RoleActeurRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.RoleActeurResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface RoleActeurService {

    RoleActeurResponse save(@Valid RoleActeurRequest request);
    RoleActeurResponse getOne(UUID id);
    RoleActeurResponse getOneByTrackingId(UUID trackingId);
    Page<RoleActeurResponse> getAll(int page, int size);
    RoleActeurResponse updateOne(@Valid RoleActeurRequest request);
    void deleteOne(UUID trackingId);
    long count();
}
