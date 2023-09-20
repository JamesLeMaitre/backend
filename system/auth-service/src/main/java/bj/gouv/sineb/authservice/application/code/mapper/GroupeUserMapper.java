package bj.gouv.sineb.authservice.application.code.mapper;


import bj.gouv.sineb.authservice.application.code.dto.request.GroupeUserRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.GroupeUserResponse;
import bj.gouv.sineb.authservice.application.code.entity.Groupe;
import bj.gouv.sineb.authservice.application.code.entity.GroupeUser;
import bj.gouv.sineb.authservice.application.code.entity.User;
import bj.gouv.sineb.authservice.application.code.repository.GroupeRepository;
import bj.gouv.sineb.authservice.application.code.repository.UserRepository;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;


@Component
public class GroupeUserMapper {

    private final UserMapper userMapper;
    private final GroupeMapper groupeMapper;
    private final GroupeRepository groupeRepository;
    private final UserRepository userRepository;


    public GroupeUserMapper(UserMapper userMapper,
                            GroupeMapper groupeMapper,
                            GroupeRepository groupeRepository,
                            UserRepository userRepository){
        this.userMapper = userMapper;
        this.groupeMapper = groupeMapper;
        this.groupeRepository = groupeRepository;
        this.userRepository = userRepository;
    }



    public GroupeUserResponse toDto(GroupeUser entity){
        if (entity!=null){
            return GroupeUserResponse.builder()
                    .trackingId(entity.getTrackingId())
                    .groupeResponse(groupeMapper.toDto(entity.getGroupe()))
                    .userResponse(userMapper.toDto(entity.getUser()))
                    .createdBy(entity.getCreatedBy())
                    .createdAt(entity.getCreatedAt())
                    .lastModifiedBy(entity.getLastModifiedBy())
                    .lastModifiedAt(entity.getLastModifiedAt())
                    .build();
        }
        return null;
    }

    public GroupeUser toEntity(GroupeUserRequest request){
        Optional<Groupe> groupeOptional = groupeRepository.findByTrackingId(request.getGroupeTrackingId());
        Optional<User> userOptional = userRepository.findByTrackingId(request.getUserTrackingId());
        if (groupeOptional.isEmpty())
            throw new CustomNotFoundException("Groupe with trackingId: "+request.getGroupeTrackingId()+" not found !");
        else if (userOptional.isEmpty())
            throw new CustomNotFoundException("User with trackingId: "+request.getUserTrackingId()+" not found !");
        else {
            return GroupeUser.builder()
                    .id(UUID.randomUUID())
                    .trackingId(UUID.randomUUID())
                    .groupe(groupeOptional.get())
                    .user(userOptional.get())
                    .deleted(false)
                    .build();
        }
    }
    
    

    /*public GroupeUserMapper(){}

    public GroupeUserRequest toDto(GroupeUser entity){
        if (entity!=null){
            GroupeUserRequest dto = new GroupeUserRequest();
            BeanUtils.copyProperties(entity, dto);
            UserRequest userRequest = new UserMapper().toDto(entity.getUser());
            GroupeRequest groupeRequest = new GroupeMapper().toDto(entity.getGroupe());
            dto.setUserRequest(userRequest);
            dto.setGroupeRequest(groupeRequest);
            return dto;
        }
        return null;
    }

    public GroupeRessourceScopeRequest toEntity(GroupeRessourceScopeRequest dto){
        return null;
    }*/

}
