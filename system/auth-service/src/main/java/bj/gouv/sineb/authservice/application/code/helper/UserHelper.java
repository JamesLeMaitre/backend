package bj.gouv.sineb.authservice.application.code.helper;

import bj.gouv.sineb.authservice.application.code.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserHelper {

    private final UserService userService;

    public UserHelper(UserService userService) {
        this.userService = userService;
    }


    public List<String> getUserScopeByTrackingId(UUID userTrackingId) {
        return userService.getUserScopesListed(userTrackingId);
    }
}
