package bj.gouv.sineb.authservice.application.code.mapper;


import bj.gouv.sineb.authservice.application.code.dto.request.PolicieRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.PolicieResponse;
import bj.gouv.sineb.authservice.application.code.entity.Policie;
import bj.gouv.sineb.authservice.application.code.entity.Ressource;
import bj.gouv.sineb.authservice.application.code.entity.Scope;
import bj.gouv.sineb.authservice.application.code.entity.User;
import bj.gouv.sineb.authservice.application.code.repository.RessourceRepository;
import bj.gouv.sineb.authservice.application.code.repository.ScopeRepository;
import bj.gouv.sineb.authservice.application.code.repository.UserRepository;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
public class PolicieMapper {


    private final RessourceMapper ressourceMapper;
    private final UserMapper userMapper;
    private final ScopeMapper scopeMapper;
    private final UserRepository userRepository;
    private final RessourceRepository ressourceRepository;
    private final ScopeRepository scopeRepository;



    public PolicieMapper(RessourceMapper ressourceMapper,
                         UserMapper userMapper,
                         ScopeMapper scopeMapper,
                         UserRepository userRepository,
                         RessourceRepository ressourceRepository,
                         ScopeRepository scopeRepository) {
        this.ressourceMapper = ressourceMapper;
        this.userMapper = userMapper;
        this.scopeMapper = scopeMapper;
        this.userRepository = userRepository;
        this.ressourceRepository = ressourceRepository;
        this.scopeRepository = scopeRepository;
    }


    public PolicieResponse toDto(Policie entity){
        if (entity!=null){
            return PolicieResponse.builder()
                    .trackingId(entity.getTrackingId())
                    .startDate(entity.getStartDate())
                    .endDate(entity.getEndDate())
                    .userResponse(userMapper.toDto(entity.getUser()))
                    .ressourceResponse(ressourceMapper.toDto(entity.getRessource()))
                    .scopeResponse(scopeMapper.toDto(entity.getScope()))
                    .createdBy(entity.getCreatedBy())
                    .createdAt(entity.getCreatedAt())
                    .lastModifiedBy(entity.getLastModifiedBy())
                    .lastModifiedAt(entity.getLastModifiedAt())
                    .build();
        }
        return null;
    }

    public List<Policie> toEntity(PolicieRequest request){
        Optional<User> userOptional = userRepository.findByTrackingId(request.getUserTrackingId());
        Optional<Ressource> ressourceOptional = ressourceRepository.findByTrackingId(request.getRessourceTrackingId());
        List<Optional<Scope>> scopeOptionalList = request.getScopeTrackingIds().stream().map(scopeRepository::findByTrackingId).toList();
        if (request.getStartDate()==null && !request.isTakeEffectNow())
            throw new CustomNotFoundException("In case these permissions don't take effect now, startDate should not be null!");
        else if (request.getStartDate()!=null && request.isTakeEffectNow())
            throw new CustomNotFoundException("Permission can not take effect now because a startDate is alreary assigned");
        else if(request.getStartDate().isAfter(request.getEndDate()))
            throw new CustomNotFoundException("StartDate should be before EndDate!");
        else if(request.getScopeTrackingIds().isEmpty())
            throw new CustomNotFoundException("One Scope at least is required !");
        else if (userOptional.isEmpty())
            throw new CustomNotFoundException("User with trackingId: "+request.getUserTrackingId()+" not found!");
        else if (ressourceOptional.isEmpty())
            throw new CustomNotFoundException("Ressource with trackingId: "+request.getRessourceTrackingId()+" not found!");
        else if (!isScopeIdsAndScopeOptionalsSizesEqual(request.getScopeTrackingIds(), scopeOptionalList)) {
            throw new CustomNotFoundException("Can not find scopeIds : "+getScopeNotFountList(request.getScopeTrackingIds())+"!");
        } else {
            List<Policie> policieList = new ArrayList<>();
            scopeOptionalList.forEach(scopeOptional -> {
                policieList.add(
                        Policie.builder()
                                .id(UUID.randomUUID())
                                .trackingId(UUID.randomUUID())
                                .startDate(request.getStartDate())
                                .endDate(request.getEndDate())
                                .user(userOptional.get())
                                .ressource(ressourceOptional.get())
                                .scope(scopeOptional.get())
                                .takeEffectNow(request.isTakeEffectNow() && request.getStartDate()==null)
                                .expired(false)
                                .deleted(false)
                                .build()
                );
            });
            return policieList;
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






    /*public PolicieMapper(){}

    public PolicieRequest toDto(Policie entity){
        if (entity!=null){
            PolicieRequest dto = new PolicieRequest();
            BeanUtils.copyProperties(entity, dto);
            List<ScopeRequest> scopeList = entity.getPolicieScopeList() != null ? entity.getPolicieScopeList().stream()
                    .map(policieScope -> new ScopeMapper().toDto(policieScope.getScope()))
                    .toList() : null;
            RessourceRequest ressourceRequestList = entity.getRessource() != null ?
                    new RessourceMapper().toDto(entity.getRessource()) : null;
            dto.setScopeRequestList(scopeList);
            dto.setRessourceRequest(ressourceRequestList);
            System.out.println("scopeList : "+scopeList);
            return dto;
        }
        return null;
    }

    public Policie toEntity(PolicieRequest dto){
        if (dto!=null){
            Policie entity = new Policie();
            BeanUtils.copyProperties(dto, entity);
            return entity;
        }
        return null;
    }*/

}
