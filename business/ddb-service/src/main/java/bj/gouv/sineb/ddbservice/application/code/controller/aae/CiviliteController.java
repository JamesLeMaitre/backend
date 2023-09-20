package bj.gouv.sineb.ddbservice.application.code.controller.aae;


import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.CiviliteResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.CiviliteRequest;
import bj.gouv.sineb.ddbservice.application.code.service.aae.CiviliteService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/api/v1/ddb/aae/civilite/")
public class CiviliteController {

    private final ApplicationCommonTask applicationCommonTask;
    private final CiviliteService service;


    public CiviliteController(ApplicationCommonTask applicationCommonTask, CiviliteService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping("save")
    public BaseResponse<CiviliteResponse> save(@RequestBody CiviliteRequest dto) {
        Instant startProcessing = Instant.now();
        CiviliteResponse civiliteResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<CiviliteResponse>builder()
                .data(civiliteResponse)
                .status(200)
                .message("Civilite created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<CiviliteResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        CiviliteResponse civiliteResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<CiviliteResponse>builder()
                .data(civiliteResponse)
                .status(200)
                .message("Civilite retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<CiviliteResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size
           ){
        Instant startProcessing = Instant.now();
        Page<CiviliteResponse> civiliteResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<CiviliteResponse>>builder()
                .data(civiliteResponsePage)
                .status(200)
                .message("Civilite list retrieved successfully!")
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
                .message("Civilite counted successfully!")
                .build();
    }

    @PutMapping("/update/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<CiviliteResponse> updateOne(@RequestBody CiviliteRequest dto, @PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        CiviliteResponse civiliteResponse = service.updateOne(dto, trackingId);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<CiviliteResponse>builder()
                .data(civiliteResponse)
                .status(200)
                .message("Civilite updated successfully!")
                .build();
    }

    @DeleteMapping("delete/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<CiviliteResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<CiviliteResponse>builder()
                .status(200)
                .message("Civilite deleted successfully!")
                .build();
    }
}
