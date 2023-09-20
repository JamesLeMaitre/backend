package bj.gouv.sineb.ddbservice.application.code.controller.common;

import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.CommuneResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.CommuneRequest;
import bj.gouv.sineb.ddbservice.application.code.service.common.CommuneService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/common/commune")
public class CommuneController {

    private final ApplicationCommonTask applicationCommonTask;
    private final CommuneService service;


    public CommuneController(ApplicationCommonTask applicationCommonTask, CommuneService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<CommuneResponse> save(@RequestBody CommuneRequest dto) {
        Instant startProcessing = Instant.now();
        CommuneResponse communeResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<CommuneResponse>builder()
                .data(communeResponse)
                .message("Commune created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<CommuneResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        CommuneResponse communeResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<CommuneResponse>builder()
                .data(communeResponse)
                .message("Commune retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<CommuneResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<CommuneResponse> communeResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<CommuneResponse>>builder()
                .data(communeResponsePage)
                .message("Commune list retrieved successfully!")
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
                .message("Commune counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<CommuneResponse> updateOne(@RequestBody CommuneRequest dto){
        Instant startProcessing = Instant.now();
        CommuneResponse communeResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<CommuneResponse>builder()
                .data(communeResponse)
                .message("Commune updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<CommuneResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<CommuneResponse>builder()
                .message("Commune deleted successfully!")
                .build();
    }
}
