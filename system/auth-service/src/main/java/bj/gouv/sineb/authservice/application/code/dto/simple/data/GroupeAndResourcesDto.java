package bj.gouv.sineb.authservice.application.code.dto.simple.data;

import bj.gouv.sineb.authservice.application.code.dto.request.GroupeRequest;
import bj.gouv.sineb.authservice.application.code.dto.request.ResourceAndScopesRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GroupeAndResourcesDto {
    private GroupeRequest groupeRequest;
    private List<ResourceAndScopesRequest> resourceAndScopesRequestList = new CopyOnWriteArrayList<>();;

}
