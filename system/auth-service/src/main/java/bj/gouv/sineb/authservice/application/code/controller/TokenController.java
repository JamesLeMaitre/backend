package bj.gouv.sineb.authservice.application.code.controller;

import bj.gouv.sineb.authservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.authservice.application.code.service.TokenService;
import bj.gouv.sineb.authservice.application.code.service.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth/token")
public class TokenController {
    private final ApplicationCommonTask applicationCommonTask;
    private final TokenService tokenService;
    private final UserService userService;
    


    public TokenController(ApplicationCommonTask applicationCommonTask, TokenService tokenService, UserService userService) {
        this.applicationCommonTask = applicationCommonTask;
        this.tokenService = tokenService;
        this.userService = userService;
    }


    //@Retry(name= AppConstant.AUTH_SERVICE, fallbackMethod = "callAccessTokenServiceInTransaction")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Map<String, String>> jwtToken(
            @RequestParam @NotNull String grantType,
            @RequestParam @NotNull String email,
            @RequestParam @NotNull String password,
            @RequestParam @NotNull boolean withRefreshToken,
            @RequestParam @NotNull String refreshToken) throws Exception {
        Instant startProcessing = Instant.now();
        ResponseEntity<Map<String, String>> jwtTokenResponse = tokenService.authenticate(grantType, email, password,withRefreshToken,refreshToken);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return jwtTokenResponse;
    }

    @GetMapping("/{userId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<String> getUserScopes(@PathVariable UUID userId) throws Exception {
        return userService.getUserScopesListed(userId);
    }

    public ResponseEntity<Map<String, String>>  callAccessTokenServiceInTransaction(Exception exception){
        HashMap<String, String> value = new HashMap<>();
        value.put("key", "Access token service seems unavailable for this registration. Please try later !");
        return ResponseEntity.ofNullable(value);
    }
}
