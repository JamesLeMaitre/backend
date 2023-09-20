package bj.gouv.sineb.authservice.application.code.controller;

import bj.gouv.sineb.authservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.authservice.application.code.dto.request.GroupeUserRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.BaseResponse;
import bj.gouv.sineb.authservice.application.code.dto.response.GroupeUserResponse;
import bj.gouv.sineb.authservice.application.code.service.GroupeUserService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/auth/groupes-users")
public class GroupeUserController {

    private final ApplicationCommonTask applicationCommonTask;
    private final GroupeUserService service;
    


    public GroupeUserController(ApplicationCommonTask applicationCommonTask, GroupeUserService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<GroupeUserResponse> save(@RequestBody GroupeUserRequest dto){
        Instant startProcessing = Instant.now();
        GroupeUserResponse groupeUserResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<GroupeUserResponse>builder()
                .data(groupeUserResponse)
                .message("GroupeUser created successfully!")
                .build();
    }

    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<GroupeUserResponse> getOneByTrackingId(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        GroupeUserResponse groupeUserResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<GroupeUserResponse>builder()
                .data(groupeUserResponse)
                .message("GroupeUser retrieved successfully!")
                .build();
    }

    @GetMapping("/by-user/{userTrackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Page<GroupeUserResponse>> getAllByUserId(
            @PathVariable UUID userTrackingId,
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size,
            @RequestParam boolean deleted
    ){
        Instant startProcessing = Instant.now();
        Page<GroupeUserResponse> groupeUserResponsePage = service.getAllByUserId(userTrackingId, page, size, deleted);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<Page<GroupeUserResponse>>builder()
                .data(groupeUserResponsePage)
                .message("GroupeUser list by email retrieved successfully!")
                .build();
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Page<GroupeUserResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size,
            @RequestParam boolean deleted
    ){
        Instant startProcessing = Instant.now();
        Page<GroupeUserResponse> groupeUserResponsePage = service.getAll(page, size, deleted);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<Page<GroupeUserResponse>>builder()
                .data(groupeUserResponsePage)
                .message("GroupeUser list retrieved successfully!")
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
                .message("GroupeUser counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<GroupeUserResponse> updateOne(@RequestBody GroupeUserRequest dto){
        Instant startProcessing = Instant.now();
        GroupeUserResponse groupeUserResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<GroupeUserResponse>builder()
                .data(groupeUserResponse)
                .message("GroupeUser updated successfully!")
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
                .message("GroupeUser deleted successfully!")
                .build();
    }
}
