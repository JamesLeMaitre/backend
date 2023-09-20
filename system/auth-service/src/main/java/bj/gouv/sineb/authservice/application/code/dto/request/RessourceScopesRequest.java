package bj.gouv.sineb.authservice.application.code.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RessourceScopesRequest {
    private List<ScopeRequest> scopeRequestList;
    private RessourceRequest ressourceRequest;

}
