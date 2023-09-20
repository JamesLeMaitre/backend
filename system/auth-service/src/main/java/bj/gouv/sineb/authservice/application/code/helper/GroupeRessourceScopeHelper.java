package bj.gouv.sineb.authservice.application.code.helper;

import bj.gouv.sineb.authservice.application.code.mapper.GroupeMapper;
import bj.gouv.sineb.authservice.application.code.dto.request.GroupeRessourceRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.GroupeRessourceScopeResponse;
import bj.gouv.sineb.authservice.application.code.dto.simple.data.RessourceScopesDto;
import bj.gouv.sineb.authservice.application.code.entity.Groupe;
import bj.gouv.sineb.authservice.application.code.entity.GroupeRessourceScope;
import bj.gouv.sineb.authservice.application.code.entity.Ressource;
import bj.gouv.sineb.authservice.application.code.entity.Scope;
import bj.gouv.sineb.authservice.application.code.mapper.RessourceMapper;
import bj.gouv.sineb.authservice.application.code.mapper.ScopeMapper;
import bj.gouv.sineb.authservice.application.code.repository.GroupeRepository;
import bj.gouv.sineb.authservice.application.code.repository.GroupeRessourceScopeRepository;
import bj.gouv.sineb.authservice.application.code.repository.RessourceRepository;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class GroupeRessourceScopeHelper {

    private final GroupeRessourceScopeRepository groupeRessourceScopeRepository;
    private final GroupeRepository groupeRepository;
    private final RessourceRepository ressourceRepository;
    private final GroupeMapper groupeMapper;
    private final RessourceMapper ressourceMapper;
    private  final ScopeMapper scopeMapper;

    public GroupeRessourceScopeHelper(GroupeRessourceScopeRepository groupeRessourceScopeRepository,
                                      GroupeRepository groupeRepository,
                                      RessourceRepository ressourceRepository,
                                      GroupeMapper groupeMapper,
                                      RessourceMapper ressourceMapper,
                                      ScopeMapper scopeMapper) {
        this.groupeRessourceScopeRepository = groupeRessourceScopeRepository;
        this.groupeRepository = groupeRepository;
        this.ressourceRepository = ressourceRepository;
        this.groupeMapper = groupeMapper;
        this.ressourceMapper = ressourceMapper;
        this.scopeMapper = scopeMapper;
    }

    public GroupeRessourceScope update(GroupeRessourceRequest request) {
        GroupeRessourceScope groupeRessourceScope = groupeRessourceScopeRepository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("GroupeRessourceScope with trackingId: "+request.getTrackingId()+" not found!"));
        Groupe groupe = groupeRepository.findByTrackingId(request.getGroupeTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("Groupe with trackingId: "+request.getGroupeTrackingId()+" not found!"));
        Ressource ressource = ressourceRepository.findByTrackingId(request.getRessourceTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("Ressource with trackingId: "+request.getRessourceTrackingId()+" not found!"));
        groupeRessourceScope.setGroupe(groupe);
        groupeRessourceScope.setRessource(ressource);

        return groupeRessourceScope;
    }

    public GroupeRessourceScopeResponse findGroupeRessourcesAndScopes(boolean deleted, UUID groupeTrackingId) {
        Groupe groupe = groupeRepository.findByTrackingId(groupeTrackingId)
                .orElseThrow(()-> new CustomNotFoundException("Groupe with trackingId: "+groupeTrackingId+" not found!"));
        List<GroupeRessourceScope> groupeRessourceScopeList =
                groupeRessourceScopeRepository.findAllByDeletedAndGroupeId(deleted, groupe.getId());

        if(groupeRessourceScopeList.isEmpty()){
            return GroupeRessourceScopeResponse.builder()
                    .groupeResponse(groupeMapper.toDto(groupe))
                    .ressourceScopesDtoList(List.of())
                    .build();
        }

        Map<UUID, List<GroupeRessourceScope>> groupeRessourceScopeByRessourceId = groupeRessourceScopeList.stream()
                .collect(Collectors.groupingBy(g->g.getRessource().getId()));

        List<RessourceScopesDto> ressourceScopesDtoList = new ArrayList<>();

        for (Map.Entry<UUID, List<GroupeRessourceScope>> entry : groupeRessourceScopeByRessourceId.entrySet()) {
            UUID ressourceId = entry.getKey();
            List<GroupeRessourceScope> groupeRessourceScopeListByRessourceId = entry.getValue();
            Ressource ressource = entry.getValue().get(0).getRessource();
            List<Scope> scopeList = groupeRessourceScopeListByRessourceId.stream().map(GroupeRessourceScope::getScope).toList();
            RessourceScopesDto ressourceScopesDto = RessourceScopesDto.builder()
                    .ressourceResponse(ressourceMapper.toDto(ressource))
                    .scopeResponseList(scopeList.stream().map(scopeMapper::toDto).toList())
                    .build();
            ressourceScopesDtoList.add(ressourceScopesDto);
        }
        return GroupeRessourceScopeResponse.builder()
                .groupeResponse(groupeMapper.toDto(groupe))
                .ressourceScopesDtoList(ressourceScopesDtoList)
                .build();
    }

    public List<GroupeRessourceScopeResponse> findAllGroupesRessourcesAndScopes(boolean deleted) {
        List<Groupe> groupeList = groupeRepository.findAllByDeleted(deleted);
        if (groupeList.isEmpty())
            throw new CustomNotFoundException("No groupes found with deleted: "+deleted);

        List<GroupeRessourceScopeResponse> groupeRessourceScopeResponseList = new ArrayList<>();

        groupeList.forEach(groupe -> groupeRessourceScopeResponseList.add(findGroupeRessourcesAndScopes(deleted, groupe.getTrackingId())));

        return groupeRessourceScopeResponseList;
    }
}
