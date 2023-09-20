package bj.gouv.sineb.authservice.application.code.service.impl;

import bj.gouv.sineb.authservice.application.code.dto.simple.data.RessourceScopesDto;
import bj.gouv.sineb.authservice.application.code.entity.*;
import bj.gouv.sineb.authservice.application.code.helper.GroupeRessourceScopeHelper;
import bj.gouv.sineb.authservice.application.code.mapper.GroupeRessourceScopeMapper;
import bj.gouv.sineb.authservice.application.code.dto.request.GroupeRessourceScopeRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.GroupeRessourceScopeResponse;
import bj.gouv.sineb.authservice.application.code.mapper.RessourceMapper;
import bj.gouv.sineb.authservice.application.code.mapper.ScopeMapper;
import bj.gouv.sineb.authservice.application.code.repository.GroupeRessourceScopeRepository;
import bj.gouv.sineb.authservice.application.code.repository.PolicieRepository;
import bj.gouv.sineb.authservice.application.code.repository.UserRepository;
import bj.gouv.sineb.authservice.application.code.service.GroupeRessourceScopeService;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GroupeRessourceScopeServiceImpl implements GroupeRessourceScopeService {

    private final GroupeRessourceScopeRepository repository;
    private final GroupeRessourceScopeMapper groupeRessourceScopeMapper;
    private final GroupeRessourceScopeHelper groupeRessourceScopeHelper;
    private final UserRepository userRepository;
    private final PolicieRepository policieRepository;
    private final RessourceMapper ressourceMapper;
    private final ScopeMapper scopeMapper;

    
    
    public GroupeRessourceScopeServiceImpl(GroupeRessourceScopeRepository repository,
                                           GroupeRessourceScopeMapper groupeRessourceScopeMapper,
                                           GroupeRessourceScopeHelper groupeRessourceScopeHelper,
                                           UserRepository userRepository, PolicieRepository policieRepository, RessourceMapper ressourceMapper, ScopeMapper scopeMapper) {
        this.repository = repository;
        this.groupeRessourceScopeMapper = groupeRessourceScopeMapper;
        this.groupeRessourceScopeHelper = groupeRessourceScopeHelper;
        this.userRepository = userRepository;
        this.policieRepository = policieRepository;
        this.ressourceMapper = ressourceMapper;
        this.scopeMapper = scopeMapper;
    }

    

    @Override
    @Transactional
    public void save(GroupeRessourceScopeRequest request) {
        log.info("Saving GroupeRessourceScope with payload: "+request.toString());
        List<GroupeRessourceScope> groupeRessourceScopeList = groupeRessourceScopeMapper.toEntityWithScopeList(request);
        List<GroupeRessourceScope> groupeRessourceScopeSavedList = repository.saveAll(groupeRessourceScopeList);
        log.info("GroupeRessourceScope is created with trackingId: {}", groupeRessourceScopeSavedList.get(0).getTrackingId());
    }

    @Override
    @Transactional(readOnly = true)
    public GroupeRessourceScopeResponse getOneGroupeRessourcesAndScopes(boolean deleted, UUID groupeTrackingId) {
        log.info("Searching for Groupe ressources and scopes with groupeId: "+groupeTrackingId);
        GroupeRessourceScopeResponse groupeRessourceScopeResponse = groupeRessourceScopeHelper
                .findGroupeRessourcesAndScopes(deleted, groupeTrackingId);
        log.info("Groupe ressources and scopes retrieved successfully!");
        return groupeRessourceScopeResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public GroupeRessourceScope getOne(UUID id) {
        log.info("Searching for GroupeRessourceScope with id: "+id);
        GroupeRessourceScope groupeRessourceScope = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("GroupeRessourceScope with id: "+id+" not found!"));
        log.info("GroupeRessourceScope is found with id: {}", groupeRessourceScope.getId());
        log.info("Returning GroupeRessourceScopeResponse with trackingId: {}", groupeRessourceScope.getTrackingId());
        return groupeRessourceScope;
    }


    @Override
    @Transactional(readOnly = true)
    public List<GroupeRessourceScopeResponse> getAllGroupesRessourcesAndScopes(boolean deleted) {
        log.info("Trying to all Groupes ressources and scopes");
        List<GroupeRessourceScopeResponse> groupeRessourceScopeResponseList = groupeRessourceScopeHelper
                .findAllGroupesRessourcesAndScopes(deleted);
        log.info("Groupe ressources and scopes list retrieved successfully!");
        return groupeRessourceScopeResponseList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupeRessourceScope> getAll(int page, int size, boolean deleted) {
        log.info("Searching for GroupeRessource list with (page, size, deleted) : "+"("+page+", "+size+", "+deleted+")");
        List<GroupeRessourceScope> groupeRessourceScopeList = repository.findAllByDeleted(deleted, PageRequest.of(page, size));
        log.info("GroupeRessourceScope list found with size: {}", groupeRessourceScopeList.size());
        log.info("Returning GroupeRessourceScopeResponse list with size: {}", groupeRessourceScopeList.size());
        return groupeRessourceScopeList;
    }

    @Override
    @Transactional
    public void addSpecificScope(UUID groupeTrackingId, UUID ressourceTrackingId, UUID scopeTrackingId) {
        log.info("Trying to add a specific scope to a groupe ressource with with payload: (groupeTrackingId, ressourceTrackingId, scopeTrackingId) : " +
                ""+"("+groupeTrackingId+", "+ressourceTrackingId+", "+scopeTrackingId+")");
        GroupeRessourceScope groupeRessourceScope = groupeRessourceScopeMapper.toEntityWithOneScope(groupeTrackingId, ressourceTrackingId, scopeTrackingId);
        repository.save(groupeRessourceScope);
        log.info("Scope added for the groupe ressource is updated with id: {}", groupeRessourceScope.getId());
        log.info("Returning GroupeRessourceScopeResponse with trackingId: {}", groupeRessourceScope.getTrackingId());
    }

    @Override
    @Transactional
    public void deleteOne(UUID groupeTrackingId, UUID ressourceTrackingId, UUID scopeTrackingId) {
        log.info("Deleting scope on ressource for a specific groupe with payload: (groupeTrackingId, ressourceTrackingId, scopeTrackingId) : " +
                ""+"("+groupeTrackingId+", "+ressourceTrackingId+", "+scopeTrackingId+")");
        GroupeRessourceScope groupeRessourceScope = repository.findByDeletedFalseAndGroupeTrackingIdAndRessourceTrackingIdAndScopeTrackingId(groupeTrackingId, ressourceTrackingId, scopeTrackingId)
                .orElseThrow(()-> new CustomNotFoundException("GroupeRessourceScope with (groupeTrackingId, ressourceTrackingId, scopeTrackingId) : " +
                        ""+"("+groupeTrackingId+", "+ressourceTrackingId+", "+scopeTrackingId+") not found!"));
        groupeRessourceScope.delete();
        repository.save(groupeRessourceScope);
        log.info("GroupeRessourceScope is deleted with id: {}", groupeRessourceScope.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countByDeletedFalse();
        log.info("GroupeRessourceScope total count : {}", count);
        return count;
    }


    @Override
    public List<String> buildUserScopeListNowFromGroupeRessourceScopes(List<GroupeRessourceScopeResponse> groupeRessourceScopeResponseList) {
        if ((groupeRessourceScopeResponseList==null) || groupeRessourceScopeResponseList.isEmpty())
            return List.of();

        List<String> response = new ArrayList<>();
        groupeRessourceScopeResponseList.forEach(groupeRessourceScopeResponse -> {
            List<RessourceScopesDto> ressourceScopesDtoList = new ArrayList<>(groupeRessourceScopeResponse.getRessourceScopesDtoList());
            response.addAll(buildUserScopeListNow(ressourceScopesDtoList));
        });

        //And now we remove all duplicate scope
        Set<String> uniqueSet = new HashSet<>();
        List<String> result = new ArrayList<>();

        for (String element : response) {
            if (uniqueSet.add(element)) {
                result.add(element);
            }
        }

        System.out.println("My Scope LIst from groupe ressource scope : "+result.toString());

        return result;
    }


    @Override
    @Transactional(readOnly = true)
    public List<String> getUserScopesListed(UUID userTrackingId) {
        User user = userRepository.findByTrackingId(userTrackingId)
                .orElseThrow(()-> new CustomNotFoundException("User with id: "+userTrackingId+" not found!"));

        List<Policie> policieList = policieRepository.findAllByDeletedFalseAndExpiredFalseAndUserId(user.getId());

        if(policieList.isEmpty())
            return List.of();

        Map<UUID, List<Policie>> groupeRessourceScopeByRessourceId = policieList.stream()
                .collect(Collectors.groupingBy(g->g.getRessource().getId()));

        List<RessourceScopesDto> ressourceScopesDtoList = new ArrayList<>();

        for (Map.Entry<UUID, List<Policie>> entry : groupeRessourceScopeByRessourceId.entrySet()) {
            UUID ressourceId = entry.getKey();
            List<Policie> policieListListByRessourceId = entry.getValue();
            Ressource ressource = entry.getValue().get(0).getRessource();
            List<Scope> scopeList = policieListListByRessourceId.stream().map(Policie::getScope).toList();
            RessourceScopesDto ressourceScopesDto = RessourceScopesDto.builder()
                    .ressourceResponse(ressourceMapper.toDto(ressource))
                    .scopeResponseList(scopeList.stream().map(scopeMapper::toDto).toList())
                    .build();
            ressourceScopesDtoList.add(ressourceScopesDto);
        }

        List<String> fromPolicies = buildUserScopeListFromPoliciesNow(ressourceScopesDtoList);
        List<String> fromGroupeRessourceScope = buildUserScopeListNowFromGroupeRessourceScopes(
                        getAllGroupesRessourcesAndScopes(false)
        );

        return  getCompleteUserScopesFromPoliciesAndGroupeRessourceScope(fromPolicies, fromGroupeRessourceScope);
    }


    private List<String> buildUserScopeListFromPoliciesNow(List<RessourceScopesDto> ressourceScopesDtoList) {
        List<String> response = new ArrayList<>();
        ressourceScopesDtoList.forEach(ressourceScopesDto -> {
            ressourceScopesDto.getScopeResponseList().forEach(scopeDto -> {
                String joinedScope = ressourceScopesDto.getRessourceResponse().getName().toUpperCase()+"_"+scopeDto.getName().toUpperCase();
                response.add(joinedScope);
            });
        });

        //And now we remove all duplicate scope
        Set<String> uniqueSet = new HashSet<>();
        List<String> result = new ArrayList<>();

        for (String element : response) {
            if (uniqueSet.add(element)) {
                result.add(element);
            }
        }

        System.out.println("My Scope LIst from policies : "+result.toString());

        return result;
    }

    private List<String> getCompleteUserScopesFromPoliciesAndGroupeRessourceScope(List<String> fromPolicies,
                                                                                  List<String> fromGroupeRessourceScope) {
        List<String> userScopesWithPossibleDuplications = new ArrayList<>();
        userScopesWithPossibleDuplications.addAll(fromPolicies);
        userScopesWithPossibleDuplications.addAll(fromGroupeRessourceScope);

        //And now we remove all duplicate scope
        Set<String> uniqueSet = new HashSet<>();
        List<String> result = new ArrayList<>();

        for (String element : userScopesWithPossibleDuplications) {
            if (uniqueSet.add(element)) {
                result.add(element);
            }
        }

        System.out.println("My complete scopes list : "+result.toString());

        return result;
    }

    private List<String> buildUserScopeListNow(List<RessourceScopesDto> ressourceScopesDtoList) {
        List<String> response = new ArrayList<>();
        ressourceScopesDtoList.forEach(ressourceScopesDto -> {
            ressourceScopesDto.getScopeResponseList().forEach(scopeDto -> {
                String joinedScope = ressourceScopesDto.getRessourceResponse().getName().toUpperCase()+"_"+scopeDto.getName().toUpperCase();
                response.add(joinedScope);
            });
        });

        //And now we remove all duplicate scope
        Set<String> uniqueSet = new HashSet<>();
        List<String> result = new ArrayList<>();

        for (String element : response) {
            if (uniqueSet.add(element)) {
                result.add(element);
            }
        }
        return result;
    }
}
