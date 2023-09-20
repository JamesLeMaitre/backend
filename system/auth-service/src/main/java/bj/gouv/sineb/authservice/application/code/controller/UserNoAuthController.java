package bj.gouv.sineb.authservice.application.code.controller;

import bj.gouv.sineb.authservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.authservice.application.code.dto.request.UserRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.BaseResponse;
import bj.gouv.sineb.authservice.application.code.dto.response.UserResponse;
import bj.gouv.sineb.authservice.application.code.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@Slf4j
@RestController
@RequestMapping("/api/auth/na/users")
public class UserNoAuthController {

    private final ApplicationCommonTask applicationCommonTask;
    private final UserService service;
    


    public UserNoAuthController(ApplicationCommonTask applicationCommonTask, UserService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    private String test(){
        return "No email test is ok ...";
    }


    //@Retry(name = AppAuthConstant.AUTH_SERVICE, fallbackMethod = "callEmailServiceInTransaction")
    @PutMapping("/request-password-reset")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Void> requestPasswordResetNoAuth(@RequestParam String email) throws Exception {
        Instant startProcessing = Instant.now();
        service.requestPasswordResetNoAuth(email);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<Void>builder()
                .message("Temporary password sent to email: "+email+"!")
                .build();
    }

    @PostMapping("/register")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<UserResponse> register(@RequestBody UserRequest dto) {
        Instant startProcessing = Instant.now();
        UserResponse userResponse = service.register(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<UserResponse>builder()
                .data(userResponse)
                .message("User registered successfully!")
                .build();
    }

    /*public ResponseEntity<BaseResponse> callEmailServiceInTransaction(Exception exception){
        return ResponseEntity.ofNullable(BaseResponse.builder()
                .event("Email service seems unavailable for this registration. Please try later !").build());
    }*/

}
