package bj.gouv.sineb.authservice.application.code.dto.response;

import bj.gouv.sineb.authservice.application.code.dto.simple.data.ResourceAndScopesDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GroupeAndResourcesResponse {
    private GroupeResponse groupeResponse;
    private List<ResourceAndScopesDto> resourceAndScopesDtoList = new ArrayList<>();

}
