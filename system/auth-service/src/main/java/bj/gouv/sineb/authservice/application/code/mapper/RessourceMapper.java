package bj.gouv.sineb.authservice.application.code.mapper;


import bj.gouv.sineb.authservice.application.code.dto.request.RessourceRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.RessourceResponse;
import bj.gouv.sineb.authservice.application.code.entity.Ressource;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class RessourceMapper {


    public RessourceResponse toDto(Ressource ressource){
        return RessourceResponse.builder()
                .trackingId(ressource.getTrackingId())
                .name(ressource.getName())
                .description(ressource.getDescription())
                .createdBy(ressource.getCreatedBy())
                .createdAt(ressource.getCreatedAt())
                .lastModifiedBy(ressource.getLastModifiedBy())
                .lastModifiedAt(ressource.getLastModifiedAt())
                .build();
    }

    public Ressource toEntity(RessourceRequest ressourceRequest){
        return Ressource.builder()
                .id(UUID.randomUUID())
                .trackingId(UUID.randomUUID())
                .name(ressourceRequest.getName())
                .description(ressourceRequest.getDescription())
                .build();
    }
    
    /*public RessourceMapper(){}

    public RessourceRequest toDto(Ressource entity){
        if (entity!=null){
            RessourceRequest dto = new RessourceRequest();
            BeanUtils.copyProperties(entity, dto);

            *//*List<GroupeDto> groupeDtoList = entity.getGroupeRessourceList() != null ? entity.getGroupeRessourceList().stream()
                    .map(groupeRessource -> new GroupeMapper().toDto(groupeRessource.getGroupe()))
                    .toList() : null;
            dto.setGroupeDtoList(groupeDtoList);*//*
            return dto;
        }
        return null;
    }

    public Ressource toEntity(RessourceRequest dto){
        if (dto!=null){
            Ressource entity = new Ressource();
            BeanUtils.copyProperties(dto, entity);
            return entity;
        }
        return null;
    }*/

}
