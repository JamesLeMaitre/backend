package bj.gouv.sineb.authservice.application.code.mapper;

import bj.gouv.sineb.authservice.application.code.dto.request.UserRoleRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.UserRoleResponse;
import bj.gouv.sineb.authservice.application.code.entity.Role;
import bj.gouv.sineb.authservice.application.code.entity.User;
import bj.gouv.sineb.authservice.application.code.entity.UserRole;
import bj.gouv.sineb.authservice.application.code.repository.RoleRepository;
import bj.gouv.sineb.authservice.application.code.repository.UserRepository;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;


@Component
public class UserRoleMapper {

    private final RoleMapper roleMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    public UserRoleMapper(RoleMapper roleMapper,
                          UserMapper userMapper,
                          UserRepository userRepository,
                          RoleRepository roleRepository){
        this.roleMapper = roleMapper;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }



    public UserRoleResponse toDto(UserRole entity){
        if (entity!=null){
            return UserRoleResponse.builder()
                    .trackingId(entity.getTrackingId())
                    .userResponse(userMapper.toDto(entity.getUser()))
                    .roleResponse(roleMapper.toDto(entity.getRole()))
                    .createdBy(entity.getCreatedBy())
                    .createdAt(entity.getCreatedAt())
                    .lastModifiedBy(entity.getLastModifiedBy())
                    .lastModifiedAt(entity.getLastModifiedAt())
                    .build();
        }
        return null;
    }

    public UserRole toEntity(UserRoleRequest request){
        Optional<User> userOptional = userRepository.findByTrackingId(request.getUserTrackingId());
        Optional<Role> roleOptional = roleRepository.findByTrackingId(request.getRoleTrackingId());
        if (userOptional.isEmpty())
            throw new CustomNotFoundException("User with trackingId: "+request.getUserTrackingId()+" not found !");
        else if (roleOptional.isEmpty())
            throw new CustomNotFoundException("Role with trackingId: "+request.getRoleTrackingId()+" not found !");
        else {
            return UserRole.builder()
                    .id(UUID.randomUUID())
                    .trackingId(UUID.randomUUID())
                    .user(userOptional.get())
                    .role(roleOptional.get())
                    .active(true)
                    .build();
        }
    }
    
    
    
    /*public UserRoleMapper(){}

    public UserRoleRequest toDto(UserRole entity){
        if (entity!=null){
            UserRoleRequest dto = new UserRoleRequest();
            BeanUtils.copyProperties(entity, dto);
            RoleRequest roleRequest = new RoleMapper().toDto(entity.getRole());
            UserRequest userRequest = new UserMapper().toDto(entity.getUser());
            dto.setRoleRequest(roleRequest);
            dto.setUserRequest(userRequest);
            return dto;
        }
        return null;
    }

    public UserRole toEntity(UserRoleRequest dto){
        return null;
    }*/

}
