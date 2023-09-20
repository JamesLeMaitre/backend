package bj.gouv.sineb.ddbservice.application.code.controller.common;


import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.EtatResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.EtatRequest;
import bj.gouv.sineb.ddbservice.application.code.service.common.EtatService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/common/etat")
public class EtatController {

    private final ApplicationCommonTask applicationCommonTask;
    private final EtatService service;


    public EtatController(ApplicationCommonTask applicationCommonTask, EtatService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<EtatResponse> save(@RequestBody EtatRequest dto) {
        Instant startProcessing = Instant.now();
        EtatResponse civiliteResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<EtatResponse>builder()
                .data(civiliteResponse)
                .message("Etat created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<EtatResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        EtatResponse civiliteResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<EtatResponse>builder()
                .data(civiliteResponse)
                .message("Etat retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<EtatResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<EtatResponse> civiliteResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<EtatResponse>>builder()
                .data(civiliteResponsePage)
                .message("Etat list retrieved successfully!")
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
                .message("Etat counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<EtatResponse> updateOne(@RequestBody EtatRequest dto){
        Instant startProcessing = Instant.now();
        EtatResponse civiliteResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<EtatResponse>builder()
                .data(civiliteResponse)
                .message("Etat updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<EtatResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<EtatResponse>builder()
                .message("Etat deleted successfully!")
                .build();
    }
}
