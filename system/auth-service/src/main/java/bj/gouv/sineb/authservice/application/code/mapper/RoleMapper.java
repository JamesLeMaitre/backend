package bj.gouv.sineb.authservice.application.code.mapper;

import bj.gouv.sineb.authservice.application.code.dto.request.RoleRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.RoleResponse;
import bj.gouv.sineb.authservice.application.code.entity.Role;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class RoleMapper {


    public RoleResponse toDto(Role role){
        return RoleResponse.builder()
                .trackingId(role.getTrackingId())
                .name(role.getName())
                .description(role.getDescription())
                .createdBy(role.getCreatedBy())
                .createdAt(role.getCreatedAt())
                .lastModifiedBy(role.getLastModifiedBy())
                .lastModifiedAt(role.getLastModifiedAt())
                .build();
    }

    public Role toEntity(RoleRequest roleRequest){
        return Role.builder()
                .id(UUID.randomUUID())
                .trackingId(UUID.randomUUID())
                .name(roleRequest.getName())
                .description(roleRequest.getDescription())
                .build();
    }
    
    /*public RoleMapper(){}

    public RoleRequest toDto(Role entity){
        if (entity!=null){
            RoleRequest dto = new RoleRequest();
            BeanUtils.copyProperties(entity, dto);
            *//*List<GroupeDto> groupeDtoList = entity.getGroupeRoleList() != null ? entity.getGroupeRoleList().stream()
                    .map(groupeRessource -> new GroupeMapper().toDto(groupeRessource.getGroupe()))
                    .toList() : null;
            dto.setGroupeDtoList(groupeDtoList);*//*
            return dto;
        }
        return null;
    }

    public Role toEntity(RoleRequest dto){
        if (dto!=null){
            Role entity = new Role();
            BeanUtils.copyProperties(dto, entity);
            return entity;
        }
        return null;
    }*/

}
