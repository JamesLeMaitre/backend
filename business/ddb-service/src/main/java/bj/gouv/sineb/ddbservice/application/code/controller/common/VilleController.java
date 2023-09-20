package bj.gouv.sineb.ddbservice.application.code.controller.common;

import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.VilleResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.VilleRequest;
import bj.gouv.sineb.ddbservice.application.code.service.common.VilleService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/common/ville")
public class VilleController {

    private final ApplicationCommonTask applicationCommonTask;
    private final VilleService service;


    public VilleController(ApplicationCommonTask applicationCommonTask, VilleService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<VilleResponse> save(@RequestBody VilleRequest dto) {
        Instant startProcessing = Instant.now();
        VilleResponse civiliteResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<VilleResponse>builder()
                .data(civiliteResponse)
                .message("Ville created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<VilleResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        VilleResponse civiliteResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<VilleResponse>builder()
                .data(civiliteResponse)
                .message("Ville retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<VilleResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<VilleResponse> civiliteResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<VilleResponse>>builder()
                .data(civiliteResponsePage)
                .message("Ville list retrieved successfully!")
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
                .message("Ville counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<VilleResponse> updateOne(@RequestBody VilleRequest dto){
        Instant startProcessing = Instant.now();
        VilleResponse civiliteResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<VilleResponse>builder()
                .data(civiliteResponse)
                .message("Ville updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<VilleResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<VilleResponse>builder()
                .message("Ville deleted successfully!")
                .build();
    }
}
