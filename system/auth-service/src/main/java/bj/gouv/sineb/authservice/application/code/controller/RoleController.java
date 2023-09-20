package bj.gouv.sineb.authservice.application.code.controller;

import bj.gouv.sineb.authservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.authservice.application.code.dto.request.RoleRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.BaseResponse;
import bj.gouv.sineb.authservice.application.code.dto.response.RoleResponse;
import bj.gouv.sineb.authservice.application.code.service.RoleService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/auth/roles")
public class RoleController {
    private final ApplicationCommonTask applicationCommonTask;
    private final RoleService service;
    


    public RoleController(ApplicationCommonTask applicationCommonTask, RoleService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }


    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<RoleResponse> save(@RequestBody RoleRequest dto){
        Instant startProcessing = Instant.now();
        RoleResponse roleResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<RoleResponse>builder()
                .data(roleResponse)
                .message("Role created successfully!")
                .build();
    }

    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<RoleResponse> getOneByTrackingId(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        RoleResponse roleResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<RoleResponse>builder()
                .data(roleResponse)
                .message("Role retrieved successfully!")
                .build();
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Page<RoleResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size,
            @RequestParam boolean deleted
    ){
        Instant startProcessing = Instant.now();
        Page<RoleResponse> roleResponsePage = service.getAll(page, size, deleted);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<Page<RoleResponse>>builder()
                .data(roleResponsePage)
                .message("Role list retrieved successfully!")
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
                .message("Role counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<RoleResponse> updateOne(@RequestBody RoleRequest dto){
        Instant startProcessing = Instant.now();
        RoleResponse roleResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<RoleResponse>builder()
                .data(roleResponse)
                .message("Role updated successfully!")
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
                .message("Role deleted successfully!")
                .build();
    }
}
