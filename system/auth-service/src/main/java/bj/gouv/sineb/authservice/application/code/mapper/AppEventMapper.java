package bj.gouv.sineb.authservice.application.code.mapper;

import bj.gouv.sineb.authservice.application.code.dto.request.AppEventRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.AppEventResponse;
import bj.gouv.sineb.authservice.application.code.entity.AppEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AppEventMapper {

    public AppEventResponse toDto(AppEvent entity){
        return AppEventResponse.builder()
                .trackingId(entity.getTrackingId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdBy(entity.getCreatedBy())
                .createdAt(entity.getCreatedAt())
                .lastModifiedBy(entity.getLastModifiedBy())
                .lastModifiedAt(entity.getLastModifiedAt())
                .build();
    }

    public AppEvent toEntity(AppEventRequest request){
        return AppEvent.builder()
                .id(UUID.randomUUID())
                .trackingId(UUID.randomUUID())
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
}
