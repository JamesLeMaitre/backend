package bj.gouv.sineb.authservice.application.code.mapper;

import bj.gouv.sineb.authservice.application.code.dto.request.SystemVariableRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.SystemVariableResponse;
import bj.gouv.sineb.authservice.application.code.entity.SystemVariable;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class SystemVariableMapper {

    public SystemVariableResponse toDto(SystemVariable systemVariable){
        return SystemVariableResponse.builder()
                .trackingId(systemVariable.getTrackingId())
                .key(systemVariable.getKey())
                .value(systemVariable.getValue())
                .description(systemVariable.getDescription())
                .description(systemVariable.getDescription())
                .createdBy(systemVariable.getCreatedBy())
                .createdAt(systemVariable.getCreatedAt())
                .lastModifiedBy(systemVariable.getLastModifiedBy())
                .lastModifiedAt(systemVariable.getLastModifiedAt())
                .build();
    }

    public SystemVariable toEntity(SystemVariableRequest appEventRequest){
        return SystemVariable.builder()
                .id(UUID.randomUUID())
                .trackingId(UUID.randomUUID())
                .key(appEventRequest.getKey())
                .value(appEventRequest.getValue())
                .description(appEventRequest.getDescription())
                .build();
    }
    

    /*public SystemVariableMapper(){}

    public SystemVariableRequest toDto(SystemVariable entity){
        if (entity!=null){
            SystemVariableRequest dto = new SystemVariableRequest();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        }
        return null;
    }

    public SystemVariable toEntity(SystemVariableRequest dto){
        if (dto!=null){
            SystemVariable entity = new SystemVariable();
            BeanUtils.copyProperties(dto, entity);
            return entity;
        }
        return null;
    }*/

}
