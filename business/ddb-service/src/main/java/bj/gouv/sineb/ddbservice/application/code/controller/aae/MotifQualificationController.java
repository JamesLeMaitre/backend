package bj.gouv.sineb.ddbservice.application.code.controller.aae;

import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.MotifQualificationResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.MotifQualificationRequest;
import bj.gouv.sineb.ddbservice.application.code.service.aae.MotifQualificationService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/aae/motif-qualification")
public class MotifQualificationController {

    private final ApplicationCommonTask applicationCommonTask;
    private final MotifQualificationService service;


    public MotifQualificationController(ApplicationCommonTask applicationCommonTask, MotifQualificationService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<MotifQualificationResponse> save(@RequestBody MotifQualificationRequest dto) {
        Instant startProcessing = Instant.now();
        MotifQualificationResponse motifQualificationResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<MotifQualificationResponse>builder()
                .data(motifQualificationResponse)
                .message("MotifQualification created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<MotifQualificationResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        MotifQualificationResponse motifQualificationResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<MotifQualificationResponse>builder()
                .data(motifQualificationResponse)
                .message("MotifQualification retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<MotifQualificationResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<MotifQualificationResponse> motifQualificationResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<MotifQualificationResponse>>builder()
                .data(motifQualificationResponsePage)
                .message("MotifQualification list retrieved successfully!")
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
                .message("MotifQualification counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<MotifQualificationResponse> updateOne(@RequestBody MotifQualificationRequest dto){
        Instant startProcessing = Instant.now();
        MotifQualificationResponse motifQualificationResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<MotifQualificationResponse>builder()
                .data(motifQualificationResponse)
                .message("MotifQualification updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<MotifQualificationResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<MotifQualificationResponse>builder()
                .message("MotifQualification deleted successfully!")
                .build();
    }
}
