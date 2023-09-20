package bj.gouv.sineb.authservice.application.code.controller;

import bj.gouv.sineb.authservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.authservice.application.code.dto.request.UserRoleRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.BaseResponse;
import bj.gouv.sineb.authservice.application.code.dto.response.UserRoleResponse;
import bj.gouv.sineb.authservice.application.code.service.UserRoleService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/auth/users-roles")
public class UserRoleController {

    private final ApplicationCommonTask applicationCommonTask;
    private final UserRoleService service;
    


    public UserRoleController(ApplicationCommonTask applicationCommonTask, UserRoleService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }



    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<UserRoleResponse> save(@RequestBody UserRoleRequest dto){
        Instant startProcessing = Instant.now();
        UserRoleResponse userRoleResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<UserRoleResponse>builder()
                .data(userRoleResponse)
                .message("UserRole created successfully!")
                .build();
    }


    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<UserRoleResponse> getOneByTrackingId(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        UserRoleResponse userRoleResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<UserRoleResponse>builder()
                .data(userRoleResponse)
                .message("UserRole retrieved successfully!")
                .build();
    }


    @GetMapping("/by-role/{roleTrackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Page<UserRoleResponse>> getAllByRoleId(
            @PathVariable UUID roleTrackingId,
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size,
            @RequestParam boolean active
    ){
        Instant startProcessing = Instant.now();
        Page<UserRoleResponse> userRoleResponsePage = service.getAllByRoleId(roleTrackingId, page, size, active);
        applicationCommonTask.logThisEvent(null , startProcessing);
        return BaseResponse.<Page<UserRoleResponse>>builder()
                .data(userRoleResponsePage)
                .message("UserRole list by role retrieved successfully!")
                .build();
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Page<UserRoleResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size,
            @RequestParam boolean active
    ){
        Instant startProcessing = Instant.now();
        Page<UserRoleResponse> userRoleResponsePage = service.getAll(page, size, active);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<Page<UserRoleResponse>>builder()
                .data(userRoleResponsePage)
                .message("UserRole list retrieved successfully!")
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
                .message("UserRole counted successfully!")
                .build();
    }



    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<UserRoleResponse> updateOne(@RequestBody UserRoleRequest dto){
        Instant startProcessing = Instant.now();
        UserRoleResponse userRoleResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<UserRoleResponse>builder()
                .data(userRoleResponse)
                .message("UserRole updated successfully!")
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
                .message("UserRole deleted successfully!")
                .build();
    }
}
