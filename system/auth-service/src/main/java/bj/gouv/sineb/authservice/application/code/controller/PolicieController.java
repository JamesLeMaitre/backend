package bj.gouv.sineb.authservice.application.code.controller;

import bj.gouv.sineb.authservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.authservice.application.code.dto.request.PolicieRequest;
import bj.gouv.sineb.authservice.application.code.dto.request.PolicieUpdateRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.BaseResponse;
import bj.gouv.sineb.authservice.application.code.dto.response.PolicieResponse;
import bj.gouv.sineb.authservice.application.code.service.PolicieService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/auth/policies")
public class PolicieController {

    private final ApplicationCommonTask applicationCommonTask;
    private final PolicieService service;


    public PolicieController(ApplicationCommonTask applicationCommonTask, PolicieService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }


    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<PolicieResponse> save(@RequestBody PolicieRequest dto){
        service.save(dto);
        return BaseResponse.<PolicieResponse>builder()
                .message("Policie created successfully!")
                .build();
    }


    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<PolicieResponse> getOneByTrackingId(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        PolicieResponse policieResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<PolicieResponse>builder()
                .data(policieResponse)
                .message("Policie retrieved successfully!")
                .build();
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Page<PolicieResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size,
            @RequestParam boolean deleted,
            @RequestParam boolean expired
    ){
        Instant startProcessing = Instant.now();
        Page<PolicieResponse> policieResponsePage = service.getAll(page, size, deleted, expired);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<Page<PolicieResponse>>builder()
                .data(policieResponsePage)
                .message("Policie list retrieved successfully!")
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
                .message("Policie counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<PolicieResponse> updateOne(@RequestBody PolicieUpdateRequest dto){
        Instant startProcessing = Instant.now();
        PolicieResponse policieResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<PolicieResponse>builder()
                .data(policieResponse)
                .message("Policie updated successfully!")
                .build();
    }

    @DeleteMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Void> deleteOne(@RequestParam UUID userTrackingId, @RequestParam UUID ressourceTrackingId, @RequestParam UUID scopeTrackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(userTrackingId, ressourceTrackingId, scopeTrackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<Void>builder()
                .message("Policie deleted successfully!")
                .build();
    }

}
