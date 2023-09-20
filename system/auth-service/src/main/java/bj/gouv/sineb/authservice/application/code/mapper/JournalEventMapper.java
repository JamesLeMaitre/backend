package bj.gouv.sineb.authservice.application.code.mapper;

import bj.gouv.sineb.authservice.application.constant.AppAuthConstant;
import bj.gouv.sineb.authservice.application.interceptor.HttpRequestResponseUtils;
import bj.gouv.sineb.authservice.application.code.dto.request.JournalEventRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.JournalEventResponse;
import bj.gouv.sineb.authservice.application.code.entity.JournalEvent;
import bj.gouv.sineb.authservice.application.code.entity.User;
import bj.gouv.sineb.authservice.application.code.repository.UserRepository;
import bj.gouv.sineb.authservice.application.code.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;


@Component
public class JournalEventMapper {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public JournalEventMapper(UserService userService,
                              UserRepository userRepository,
                              UserMapper userMapper){
        this.userService = userService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public JournalEventResponse toDto(JournalEvent entity){
        if (entity!=null){
            return JournalEventResponse.builder()
                    .trackingId(entity.getTrackingId())
                    .userAgent(entity.getUserAgent())
                    .contentType(entity.getContentType())
                    .contentLength(entity.getContentLength())
                    .method(entity.getMethod())
                    .requestUrl(entity.getRequestUrl())
                    .requestUri(entity.getRequestUri())
                    .queryString(entity.getQueryString())
                    .protocol(entity.getProtocol())
                    .remoteAddr(entity.getRemoteAddr())
                    .remotePort(entity.getRemotePort())
                    .localAddr(entity.getLocalAddr())
                    .localPort(entity.getLocalPort())
                    .scheme(entity.getScheme())
                    .timestamp(entity.getTimestamp())
                    .processingTimeMillis(entity.getProcessingTimeMillis())
                    .statusCode(entity.getStatusCode())
                    .body(entity.getBody())
                    .userOfActionTrackingId(entity.getUserOfActionTrackingId())
                    .userResponse(userService.getOneByEmailForTheOnlyUsageOfJournalEvent(entity.getCreatedBy()))
                    .createdBy(entity.getCreatedBy())
                    .createdAt(entity.getCreatedAt())
                    .build();
        }
        return null;
    }

    public JournalEvent toEntity(JournalEventRequest request){
        Optional<User> userOptional = userRepository.findByEmail(HttpRequestResponseUtils.getLoggedInUserName());
        String userOfActionTrackingId;
        if (userOptional.isPresent())
            userOfActionTrackingId = userMapper.toDto(userOptional.get()).getTrackingId().toString();
        else
            userOfActionTrackingId = AppAuthConstant.SYSTEM_USER_TRACKING_ID.toString();

        return JournalEvent.builder()
                .id(UUID.randomUUID())
                .trackingId(UUID.randomUUID())
                .userAgent(request.getUserAgent())
                .contentType(request.getContentType())
                .contentLength(request.getContentLength())
                .method(request.getMethod())
                .requestUrl(request.getRequestUrl())
                .requestUri(request.getRequestUri())
                .queryString(request.getQueryString())
                .protocol(request.getProtocol())
                .remoteAddr(request.getRemoteAddr())
                .remotePort(request.getRemotePort())
                .localAddr(request.getLocalAddr())
                .localPort(request.getLocalPort())
                .scheme(request.getScheme())
                .timestamp(request.getTimestamp())
                .processingTimeMillis(request.getProcessingTimeMillis())
                .statusCode(request.getStatusCode())
                .userOfActionTrackingId(userOfActionTrackingId)
                .body(request.getBody())
                .build();
    }

}
