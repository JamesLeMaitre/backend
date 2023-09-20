package bj.gouv.sineb.ddbservice.application.code.controller.common;


import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.ArrondissementResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.ArrondissementRequest;
import bj.gouv.sineb.ddbservice.application.code.service.common.ArrondissementService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/common/arrondissement")
public class ArrondissementController {

    private final ApplicationCommonTask applicationCommonTask;
    private final ArrondissementService service;


    public ArrondissementController(ApplicationCommonTask applicationCommonTask, ArrondissementService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<ArrondissementResponse> save(@RequestBody ArrondissementRequest dto) {
        Instant startProcessing = Instant.now();
        ArrondissementResponse arrondissementResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<ArrondissementResponse>builder()
                .data(arrondissementResponse)
                .message("Arrondissement created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ArrondissementResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        ArrondissementResponse arrondissementResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<ArrondissementResponse>builder()
                .data(arrondissementResponse)
                .message("Arrondissement retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<ArrondissementResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<ArrondissementResponse> arrondissementResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<ArrondissementResponse>>builder()
                .data(arrondissementResponsePage)
                .message("Arrondissement list retrieved successfully!")
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
                .message("Arrondissement counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ArrondissementResponse> updateOne(@RequestBody ArrondissementRequest dto){
        Instant startProcessing = Instant.now();
        ArrondissementResponse arrondissementResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<ArrondissementResponse>builder()
                .data(arrondissementResponse)
                .message("Arrondissement updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ArrondissementResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<ArrondissementResponse>builder()
                .message("Arrondissement deleted successfully!")
                .build();
    }
}
