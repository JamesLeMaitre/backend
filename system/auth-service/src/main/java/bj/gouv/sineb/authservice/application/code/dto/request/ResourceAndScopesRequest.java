package bj.gouv.sineb.authservice.application.code.dto.request;

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
public class ResourceAndScopesRequest {
    private RessourceRequest ressourceRequest;
    private List<ScopeRequest> scopeRequestList = new CopyOnWriteArrayList<>();;

}
