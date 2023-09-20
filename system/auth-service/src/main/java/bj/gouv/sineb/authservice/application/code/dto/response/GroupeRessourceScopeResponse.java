package bj.gouv.sineb.authservice.application.code.dto.response;

import bj.gouv.sineb.authservice.application.code.dto.simple.data.RessourceScopesDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GroupeRessourceScopeResponse {
    private GroupeResponse groupeResponse;
    private List<RessourceScopesDto> ressourceScopesDtoList;
}
