package bj.gouv.sineb.authservice.application.code.mapper;

import bj.gouv.sineb.authservice.application.code.dto.request.GroupeRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.GroupeResponse;
import bj.gouv.sineb.authservice.application.code.entity.Groupe;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GroupeMapper {

    public GroupeResponse toDto(Groupe entity){
        return GroupeResponse.builder()
                .trackingId(entity.getTrackingId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdBy(entity.getCreatedBy())
                .createdAt(entity.getCreatedAt())
                .lastModifiedBy(entity.getLastModifiedBy())
                .lastModifiedAt(entity.getLastModifiedAt())
                .build();
    }

    public Groupe toEntity(GroupeRequest request){
        return Groupe.builder()
                .id(UUID.randomUUID())
                .trackingId(UUID.randomUUID())
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
    
    
    /*public GroupeMapper(){}

    public GroupeRequest toDto(Groupe entity){
        if (entity!=null){
            GroupeRequest dto = new GroupeRequest();
            BeanUtils.copyProperties(entity, dto);
            *//*List<UserDto> userDtoList = entity.getGroupeUserList() != null ? entity.getGroupeUserList().stream()
                    .map(groupeUser -> new UserMapper().toDto(groupeUser.getUser()))
                    .toList() : null ;
            List<RessourceDto> ressourceDtoList = entity.getGroupeRessourceList() != null ? entity.getGroupeRessourceList().stream()
                    .map(groupeRessource -> new RessourceMapper().toDto(groupeRessource.getRessource()))
                    .toList() : null;
            List<RoleDto> roleDtoList = entity.getGroupeRoleList() != null ? entity.getGroupeRoleList().stream()
                    .map(groupeRole -> new RoleMapper().toDto(groupeRole.getRole()))
                    .toList() : null;
            dto.setUserDtoList(userDtoList);
            dto.setRessourceDtoList(ressourceDtoList);
            dto.setRoleDtoList(roleDtoList);*//*
            return dto;
        }
        return null;
    }

    public Groupe toEntity(GroupeRequest dto){
        if (dto!=null){
            Groupe entity = new Groupe();
            BeanUtils.copyProperties(dto, entity);
            return entity;
        }
        return null;
    }*/
}
