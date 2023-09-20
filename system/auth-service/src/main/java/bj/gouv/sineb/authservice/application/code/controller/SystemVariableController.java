package bj.gouv.sineb.authservice.application.code.controller;

import bj.gouv.sineb.authservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.authservice.application.code.dto.request.SystemVariableRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.BaseResponse;
import bj.gouv.sineb.authservice.application.code.dto.response.SystemVariableResponse;
import bj.gouv.sineb.authservice.application.code.service.SystemVariableService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/auth/system-variables")
public class SystemVariableController {

    private final ApplicationCommonTask applicationCommonTask;
    private final SystemVariableService service;
    


    public SystemVariableController(ApplicationCommonTask applicationCommonTask, SystemVariableService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }


    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<SystemVariableResponse> save(@RequestBody SystemVariableRequest dto){
        Instant startProcessing = Instant.now();
        SystemVariableResponse systemVariableResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<SystemVariableResponse>builder()
                .data(systemVariableResponse)
                .message("SystemVariable created successfully!")
                .build();
    }


    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<SystemVariableResponse> getOneByTrackingId(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        SystemVariableResponse systemVariableResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<SystemVariableResponse>builder()
                .data(systemVariableResponse)
                .message("SystemVariable retrieved successfully!")
                .build();
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Page<SystemVariableResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size,
            @RequestParam boolean deleted
    ){
        Instant startProcessing = Instant.now();
        Page<SystemVariableResponse> systemVariableResponsePage = service.getAll(page, size, deleted);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<Page<SystemVariableResponse>>builder()
                .data(systemVariableResponsePage)
                .message("SystemVariable list retrieved successfully!")
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
                .message("SystemVariable counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<SystemVariableResponse> updateOne(@RequestBody SystemVariableRequest dto){
        Instant startProcessing = Instant.now();
        SystemVariableResponse systemVariableResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<SystemVariableResponse>builder()
                .data(systemVariableResponse)
                .message("SystemVariable updated successfully!")
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
                .message("SystemVariable deleted successfully!")
                .build();
    }
}
