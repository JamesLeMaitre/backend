package bj.gouv.sineb.authservice.application.code.dto.simple.data;

import bj.gouv.sineb.authservice.application.code.dto.response.RessourceResponse;
import bj.gouv.sineb.authservice.application.code.dto.response.ScopeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RessourceScopesDto {
    private RessourceResponse ressourceResponse;
    private List<ScopeResponse> scopeResponseList;
}
