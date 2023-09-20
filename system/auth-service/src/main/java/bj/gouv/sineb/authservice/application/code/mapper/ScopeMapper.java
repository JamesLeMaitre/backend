package bj.gouv.sineb.authservice.application.code.mapper;

import bj.gouv.sineb.authservice.application.code.dto.request.ScopeRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.ScopeResponse;
import bj.gouv.sineb.authservice.application.code.entity.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class ScopeMapper {


    public ScopeResponse toDto(Scope scope){
        return ScopeResponse.builder()
                .trackingId(scope.getTrackingId())
                .name(scope.getName())
                .description(scope.getDescription())
                .createdBy(scope.getCreatedBy())
                .createdAt(scope.getCreatedAt())
                .lastModifiedBy(scope.getLastModifiedBy())
                .lastModifiedAt(scope.getLastModifiedAt())
                .build();
    }

    public Scope toEntity(ScopeRequest scopeRequest){
        return Scope.builder()
                .id(UUID.randomUUID())
                .trackingId(UUID.randomUUID())
                .name(scopeRequest.getName())
                .description(scopeRequest.getDescription())
                .build();
    }



    
    /*public ScopeMapper(){}

    public ScopeRequest toDto(Scope entity){
        if (entity!=null){
            ScopeRequest dto = new ScopeRequest();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        }
        return null;
    }

    public Scope toEntity(ScopeRequest dto){
        if (dto!=null){
            Scope entity = new Scope();
            BeanUtils.copyProperties(dto, entity);
            return entity;
        }
        return null;
    }*/

}
