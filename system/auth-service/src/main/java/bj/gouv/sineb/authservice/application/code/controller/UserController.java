package bj.gouv.sineb.authservice.application.code.controller;

import bj.gouv.sineb.authservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.authservice.application.code.dto.request.UserRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.BaseResponse;
import bj.gouv.sineb.authservice.application.code.dto.response.UserResponse;
import bj.gouv.sineb.authservice.application.code.service.UserService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/auth/users")
public class UserController {

    private final ApplicationCommonTask applicationCommonTask;
    private final UserService service;
    


    public UserController(ApplicationCommonTask applicationCommonTask, UserService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }


    //@Retry(name = AppAuthConstant.AUTH_SERVICE, fallbackMethod = "callEmailServiceInTransaction")
    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<UserResponse> create(@RequestBody UserRequest dto) throws Exception {
        Instant startProcessing = Instant.now();
        UserResponse userResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<UserResponse>builder()
                .data(userResponse)
                .message("User created successfully!")
                .build();
    }

    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<UserResponse> getOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        UserResponse userResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<UserResponse>builder()
                .data(userResponse)
                .message("User retrieved successfully!")
                .build();
    }

    // ONLY FOR TESTING. JUST WANT TO KNOW IF IT WORK WHEN I CALL IT FROM AuditorAwareImpl
    // AND IT PRODUCE INFINIT LOOP. NORMAL BECAUSE WE RETURN THE ENTITY INSTEED OF DTO
    /*@GetMapping("/email")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<User> getOneByEmail(@RequestParam String email){
        return BaseResponse.<User>builder()
                .data(service.getUserByEmail(email).get())
                .event("User retrieved successfully!")
                .build();
    }*/



    @GetMapping("/scopes/{userTrackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<String> getUserScopes(@PathVariable UUID userTrackingId) {
        Instant startProcessing = Instant.now();
        List<String> userScopesList = service.getUserScopesListed(userTrackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return userScopesList;
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Page<UserResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size,
            @RequestParam boolean enabled,
            @RequestParam boolean deleted
    ){
        Instant startProcessing = Instant.now();
        Page<UserResponse> userResponsePage = service.getAll(page, size, enabled, deleted);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<Page<UserResponse>>builder()
                .data(userResponsePage)
                .message("User list retrieved successfully!")
                .build();
    }

    @GetMapping("/connected")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Page<UserResponse>> getAllConnected(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size,
            @RequestParam boolean credentialsExpired
    ){
        Instant startProcessing = Instant.now();
        Page<UserResponse> userResponse = service.getAllConnected(page, size, credentialsExpired);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<Page<UserResponse>>builder()
                .data(userResponse)
                .message("User connected list retrieved successfully!")
                .build();
    }

    // NOT SURE ABOUT THIS RESULT. I WILL CHECK LATER
    @GetMapping("/email-history")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<String>> getUserEmailChangeHistory(@RequestParam String email){
        Instant startProcessing = Instant.now();
        List<String> result = service.getUserEmailChangeHistory(email);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<List<String>>builder()
                .data(result)
                .message("User connected list retrieved successfully!")
                .build();
    }

    @GetMapping("/count")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Long> count(){
        Instant startProcessing = Instant.now();
        Long count = service.count();
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<Long>builder()
                .data(count)
                .message("User counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<UserResponse> updateOne(@RequestBody UserRequest dto){
        Instant startProcessing = Instant.now();
        UserResponse userResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<UserResponse>builder()
                .data(userResponse)
                .message("User updated successfully!")
                .build();
    }

    @PutMapping("/change-password")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Void> changePassword(@RequestParam String email, @RequestParam String currentPassword, @RequestParam String newPassword) throws Exception {
        Instant startProcessing = Instant.now();
        service.changePassword(email, currentPassword, newPassword);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<Void>builder()
                .message("User password changed successfully!")
                .build();
    }


    @PostMapping("/validate-account")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Void> validateAccount(@RequestParam String email, @RequestParam String code) throws Exception {
        Instant startProcessing = Instant.now();
        service.validateAccount(email, code);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<Void>builder()
                .message("User account validated successfully!")
                .build();
    }

    //@Retry(name = AppAuthConstant.AUTH_SERVICE, fallbackMethod = "callEmailServiceInTransaction")
    @PostMapping("/ask-new-validate-account")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Void> askNewValidationCode(@RequestParam String email) throws Exception {
        Instant startProcessing = Instant.now();
        service.askNewValidationCode(email);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<Void>builder()
                .message("User new validation code sent via email successfully!")
                .build();
    }


    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Void> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<Void>builder()
                .message("User deleted successfully!")
                .build();
    }

    @DeleteMapping("/disable/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Void> disableOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.disableOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<Void>builder()
                .message("User disable successfully!")
                .build();
    }





    /*
        DANS LE SINEB LES UTILISATEURS UTILISENT DES ADRESSES EMAIL SINEB.
        CES ADDRESSES NE SONT PAS APPELLEES A ETRE CHANGEES.
        DANS LE CAS CONTRAIRE IL FAUDRA JUSTE DECOMMENTER CE CODE ET C'EST OK !
    */
    /*@PutMapping("/event-email-reset")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Void> requestEmailReset(@RequestParam String newEmail) throws Exception {
        service.requestEmailReset(newEmail);
        return BaseResponse.<Void>builder()
                .event("User email reset email sent successfully!")
                .build();
    }

    @PutMapping("/finish-email-reset")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Void> finishEmailReset(@RequestParam String code) throws Exception {
        Instant startProcessing = Instant.now();
        service.finishEmailReset(code);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<Void>builder()
                .event("Email reset completed successfully!")
                .build();
    }*/
}
