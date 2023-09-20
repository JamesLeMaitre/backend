package bj.gouv.sineb.authservice.application.code.mapper;

import bj.gouv.sineb.authservice.application.code.dto.request.GroupeRessourceScopeRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.GroupeRessourceScopeResponse;
import bj.gouv.sineb.authservice.application.code.entity.Groupe;
import bj.gouv.sineb.authservice.application.code.entity.GroupeRessourceScope;
import bj.gouv.sineb.authservice.application.code.entity.Ressource;
import bj.gouv.sineb.authservice.application.code.entity.Scope;
import bj.gouv.sineb.authservice.application.code.repository.GroupeRepository;
import bj.gouv.sineb.authservice.application.code.repository.GroupeRessourceScopeRepository;
import bj.gouv.sineb.authservice.application.code.repository.RessourceRepository;
import bj.gouv.sineb.authservice.application.code.repository.ScopeRepository;
import bj.gouv.sineb.authservice.application.code.helper.GroupeRessourceScopeHelper;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class GroupeRessourceScopeMapper {

    private final GroupeRepository groupeRepository;
    private final RessourceRepository ressourceRepository;
    private final ScopeRepository scopeRepository;
    private final GroupeRessourceScopeRepository groupeRessourceScopeRepository;
    private final GroupeMapper groupeMapper;
    private final RessourceMapper ressourceMapper;
    private final ScopeMapper scopeMapper;
    private final GroupeRessourceScopeHelper groupeRessourceScopeHelper;

    public GroupeRessourceScopeMapper(GroupeRepository groupeRepository,
                                      RessourceRepository ressourceRepository,
                                      ScopeRepository scopeRepository,
                                      GroupeRessourceScopeRepository groupeRessourceScopeRepository,
                                      GroupeMapper groupeMapper,
                                      RessourceMapper ressourceMapper,
                                      ScopeMapper scopeMapper,
                                      GroupeRessourceScopeHelper groupeRessourceScopeHelper) {
        this.groupeRepository = groupeRepository;
        this.ressourceRepository = ressourceRepository;
        this.scopeRepository = scopeRepository;
        this.groupeRessourceScopeRepository = groupeRessourceScopeRepository;
        this.groupeMapper = groupeMapper;
        this.ressourceMapper = ressourceMapper;
        this.scopeMapper = scopeMapper;
        this.groupeRessourceScopeHelper = groupeRessourceScopeHelper;
    }



    public GroupeRessourceScopeResponse toDto(GroupeRessourceScope entity){
        if (entity!=null){
            return GroupeRessourceScopeResponse.builder().build();
        } // We dont need this for the moment
        return null;
    }



    public List<GroupeRessourceScope> toEntityWithScopeList(GroupeRessourceScopeRequest request){
        Optional<Groupe> groupeOptional = groupeRepository.findByTrackingId(request.getGroupeTrackingId());
        Optional<Ressource> ressourceOptional = ressourceRepository.findByTrackingId(request.getRessourceTrackingId());
        List<Optional<Scope>> scopeOptionalList = request.getScopeTrackingIds().stream().map(scopeRepository::findByTrackingId).toList();

        if(request.getScopeTrackingIds().isEmpty())
            throw new CustomNotFoundException("One Scope at least is required !");
        else if (groupeOptional.isEmpty())
            throw new CustomNotFoundException("Groupe with trackingId: "+request.getGroupeTrackingId()+" not found !");
        else if (ressourceOptional.isEmpty())
            throw new CustomNotFoundException("Ressource with trackingId: "+request.getRessourceTrackingId()+" not found !");
        else if (!isScopeIdsAndScopeOptionalsSizesEqual(request.getScopeTrackingIds(), scopeOptionalList))
            throw new CustomNotFoundException("Can not find scopeIds : "+getScopeNotFountList(request.getScopeTrackingIds())+"!");
        else {
            List<GroupeRessourceScope> groupeRessourceScopeList = new ArrayList<>();
            UUID commonTrackingId = UUID.randomUUID();
            scopeOptionalList.forEach(scopeOptional -> {
                groupeRessourceScopeList.add(
                        GroupeRessourceScope.builder()
                                .id(UUID.randomUUID())
                                .trackingId(commonTrackingId)
                                .groupe(groupeOptional.get())
                                .ressource(ressourceOptional.get())
                                .scope(scopeOptional.get())
                                .deleted(false)
                                .build()
                );
            });
            return groupeRessourceScopeList;
        }
    }



    public GroupeRessourceScope toEntityWithOneScope(UUID groupeTrackingId, UUID ressourceTrackingId, UUID scopeTrackingId){
        Optional<Groupe> groupeOptional = groupeRepository.findByTrackingId(groupeTrackingId);
        Optional<Ressource> ressourceOptional = ressourceRepository.findByTrackingId(ressourceTrackingId);
        Optional<Scope> scopeOptional = scopeRepository.findByTrackingId(scopeTrackingId);

        if (groupeOptional.isEmpty())
            throw new CustomNotFoundException("Groupe with trackingId: "+groupeTrackingId+" not found !");
        else if (ressourceOptional.isEmpty())
            throw new CustomNotFoundException("Ressource with trackingId: "+ressourceTrackingId+" not found !");
        else if (scopeOptional.isEmpty())
            throw new CustomNotFoundException("Scope with trackingId: "+scopeTrackingId+" not found !");
        else {
            Optional<GroupeRessourceScope> groupeRessourceScopeOptional = Optional.ofNullable(groupeRessourceScopeRepository.findFirstByGroupeIdAndRessourceId(
                            groupeOptional.get().getId(), ressourceOptional.get().getId())
                    .orElseThrow(() -> new CustomNotFoundException("Groupe with trackingId: " + groupeTrackingId + " doesn't have access to ressource with " +
                            "trackingId: " + ressourceTrackingId + " not found !")));

            return GroupeRessourceScope.builder()
                    .id(UUID.randomUUID())
                    .trackingId(groupeRessourceScopeOptional.get().getTrackingId())
                    .groupe(groupeOptional.get())
                    .ressource(ressourceOptional.get())
                    .scope(scopeOptional.get())
                    .deleted(false)
                    .build();
        }
    }



    private boolean isScopeIdsAndScopeOptionalsSizesEqual(List<UUID> scopeTrackingIds, List<Optional<Scope>> scopeOptionals){
        return scopeOptionals.stream().filter(Optional::isPresent).count() == scopeTrackingIds.size();
    }

    private String getScopeNotFountList(List<UUID> scopeTrackingIds){
        return scopeTrackingIds.stream()
                .filter(scopeTrackingId -> scopeRepository.findByTrackingId(scopeTrackingId).isEmpty())
                .map(UUID::toString)
                .collect(Collectors.joining(";"));
    }




    /*public GroupeRessourceScopeRequest toDto(GroupeRessourceScope entity){
        if (entity!=null){
            ScopeScopeRessourceScopeRequest dto = new ScopeScopeRessourceScopeRequest();
            ScopeScopeRessourceRequest scopeScopeRessourceRequest = entity.getScopeScopeRessource()!=null ?
                    new ScopeScopeRessourceMapper(scopeRessourceMapper, scopeMapper, scopeService, scopeRessourceService, scopeRepository, scopeRessourceRepository).toDto(entity.getScopeScopeRessource()) : null;
            ScopeRequest scopeRequest = entity.getScope()!=null ?
                    new ScopeMapper().toDto(entity.getScope()) : null;
            BeanUtils.copyProperties(entity, dto);
            dto.setScopeScopeRessourceRequest(scopeScopeRessourceRequest);
            dto.setScopeRequest(scopeRequest);
            return dto;
        }
        return null;
   }

    public ScopeScopeRessourceScope toEntity(ScopeScopeRessourceScopeRequest dto){
        return null;
   }*/

}
