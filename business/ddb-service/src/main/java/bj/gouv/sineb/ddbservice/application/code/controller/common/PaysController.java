package bj.gouv.sineb.ddbservice.application.code.controller.common;

import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.PaysResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.PaysRequest;
import bj.gouv.sineb.ddbservice.application.code.service.common.PaysService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/ddb/common/pays")
public class PaysController {

    private final ApplicationCommonTask applicationCommonTask;
    private final PaysService service;


    public PaysController(ApplicationCommonTask applicationCommonTask, PaysService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping("save")
    public BaseResponse<PaysResponse> save(@RequestBody PaysRequest dto) {
        Instant startProcessing = Instant.now();
        PaysResponse paysResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<PaysResponse>builder()
                .data(paysResponse)
                .status(200)
                .message("Pays created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<PaysResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        PaysResponse paysResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<PaysResponse>builder()
                .data(paysResponse)
                .status(200)
                .message("Pays retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<PaysResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size,
            @RequestParam boolean deleted){
        Instant startProcessing = Instant.now();
        Page<PaysResponse> paysResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<PaysResponse>>builder()
                .data(paysResponsePage)
                .status(200)
                .message("Pays list retrieved successfully!")
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
                .status(200)
                .message("Pays counted successfully!")
                .build();
    }

    @PutMapping("/update/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<PaysResponse> updateOne(@RequestBody PaysRequest dto, @PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        PaysResponse paysResponse = service.updateOne(dto, trackingId);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<PaysResponse>builder()
                .data(paysResponse)
                .status(200)
                .message("Pays updated successfully!")
                .build();
    }

    @DeleteMapping("/delete/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<PaysResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<PaysResponse>builder()
                .status(200)
                .message("Pays deleted successfully!")
                .build();
    }
}
