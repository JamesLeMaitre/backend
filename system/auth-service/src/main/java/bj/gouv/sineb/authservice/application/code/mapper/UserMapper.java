package bj.gouv.sineb.authservice.application.code.mapper;

import bj.gouv.sineb.authservice.application.code.dto.request.UserRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.RoleResponse;
import bj.gouv.sineb.authservice.application.code.dto.response.UserResponse;
import bj.gouv.sineb.authservice.application.code.entity.User;
import bj.gouv.sineb.authservice.application.code.entity.UserRole;
import bj.gouv.sineb.authservice.application.code.repository.UserRoleRepository;
import bj.gouv.sineb.authservice.application.code.service.GroupeRessourceScopeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Slf4j
@Component
public class UserMapper {

    private final PasswordEncoder encoder;
    private final RoleMapper roleMapper;
    private final UserRoleRepository userRoleRepository;
    private final GroupeRessourceScopeService groupeRessourceScopeService;

    public UserMapper(PasswordEncoder encoder, RoleMapper roleMapper, UserRoleRepository userRoleRepository, GroupeRessourceScopeService groupeRessourceScopeService) {
        this.encoder = encoder;
        this.roleMapper = roleMapper;
        this.userRoleRepository = userRoleRepository;
        this.groupeRessourceScopeService = groupeRessourceScopeService;
    }


    public UserResponse toDto(User user){

        List<String> scopes = groupeRessourceScopeService.getUserScopesListed(user.getTrackingId());

        RoleResponse roleResponse = null;
        List<UserRole> userRoles = userRoleRepository.findAllByActiveAndUserId(true, user.getId());
        if (!userRoles.isEmpty())
            roleResponse = roleMapper.toDto(userRoles.get(0).getRole());


        return UserResponse.builder()
                .trackingId(user.getTrackingId())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .phone(user.getPhone())
                .roleResponse(roleResponse)
                .scopes(scopes)
                .createdBy(user.getCreatedBy())
                .createdAt(user.getCreatedAt())
                .lastModifiedBy(user.getLastModifiedBy())
                .lastModifiedAt(user.getLastModifiedAt())
                .build();
    }

    public UserResponse toDtoWithoutRelations(User user){

        List<String> scopes = groupeRessourceScopeService.getUserScopesListed(user.getTrackingId());

        RoleResponse roleResponse = null;
        List<UserRole> userRoles = userRoleRepository.findAllByActiveAndUserId(true, user.getId());
        if (!userRoles.isEmpty())
            roleResponse = roleMapper.toDto(userRoles.get(0).getRole());


        return UserResponse.builder()
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .phone(user.getPhone())
                .build();
    }



    public User toEntity(UserRequest request){
        return User.builder()
                .id(UUID.randomUUID())
                .trackingId(UUID.randomUUID())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .phone(request.getPhone())
                .deleted(false)
                .enabled(true)
                .passwordChangedOnce(false)
                .accountNonLocked(false)
                .accountNonExpired(true)
                .credentialsNonExpired(false)
                .build();
    }







    /*public UserMapper(){}

    public UserRequest toDto(User entity){
        if (entity!=null){
            UserRequest dto = new UserRequest();
            BeanUtils.copyProperties(entity, dto);
            *//*List<GroupeDto> groupeDtoList = entity.getGroupeUserList() != null ? entity.getGroupeUserList().stream()
                    .map(groupeRessource -> new GroupeMapper().toDto(groupeRessource.getGroupe()))
                    .toList() : null;
            dto.setGroupeDtoList(groupeDtoList);*//*
            dto.setPassword(null);
            return dto;
        }
        return null;
    }

    public User toEntity(UserRequest dto){
        if (dto!=null){
            User entity = new User();
            BeanUtils.copyProperties(dto, entity);
            return entity;
        }
        return null;
    }*/

}
