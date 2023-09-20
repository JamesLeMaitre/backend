package bj.gouv.sineb.ddbservice.application.code.controller.common;

import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.ComiteResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.ComiteRequest;
import bj.gouv.sineb.ddbservice.application.code.service.common.ComiteService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/common/comite")
public class ComiteController {

    private final ApplicationCommonTask applicationCommonTask;
    private final ComiteService service;


    public ComiteController(ApplicationCommonTask applicationCommonTask, ComiteService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<ComiteResponse> save(@RequestBody ComiteRequest dto) {
        Instant startProcessing = Instant.now();
        ComiteResponse civiliteResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<ComiteResponse>builder()
                .data(civiliteResponse)
                .message("Comite created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ComiteResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        ComiteResponse civiliteResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<ComiteResponse>builder()
                .data(civiliteResponse)
                .message("Comite retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<ComiteResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<ComiteResponse> civiliteResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<ComiteResponse>>builder()
                .data(civiliteResponsePage)
                .message("Comite list retrieved successfully!")
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
                .message("Comite counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ComiteResponse> updateOne(@RequestBody ComiteRequest dto){
        Instant startProcessing = Instant.now();
        ComiteResponse civiliteResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<ComiteResponse>builder()
                .data(civiliteResponse)
                .message("Comite updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ComiteResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<ComiteResponse>builder()
                .message("Comite deleted successfully!")
                .build();
    }
}
