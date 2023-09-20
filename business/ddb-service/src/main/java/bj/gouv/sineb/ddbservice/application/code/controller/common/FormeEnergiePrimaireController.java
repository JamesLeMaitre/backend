package bj.gouv.sineb.ddbservice.application.code.controller.common;


import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.FormeEnergiePrimaireResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.FormeEnergiePrimaireRequest;
import bj.gouv.sineb.ddbservice.application.code.service.common.FormeEnergiePrimaireService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/common/forme-energie-primaire")
public class FormeEnergiePrimaireController {

    private final ApplicationCommonTask applicationCommonTask;
    private final FormeEnergiePrimaireService service;


    public FormeEnergiePrimaireController(ApplicationCommonTask applicationCommonTask, FormeEnergiePrimaireService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<FormeEnergiePrimaireResponse> save(@RequestBody FormeEnergiePrimaireRequest dto) {
        Instant startProcessing = Instant.now();
        FormeEnergiePrimaireResponse formeEnergiePrimaireResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<FormeEnergiePrimaireResponse>builder()
                .data(formeEnergiePrimaireResponse)
                .message("FormeEnergiePrimaire created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<FormeEnergiePrimaireResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        FormeEnergiePrimaireResponse formeEnergiePrimaireResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<FormeEnergiePrimaireResponse>builder()
                .data(formeEnergiePrimaireResponse)
                .message("FormeEnergiePrimaire retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<FormeEnergiePrimaireResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<FormeEnergiePrimaireResponse> formeEnergiePrimaireResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<FormeEnergiePrimaireResponse>>builder()
                .data(formeEnergiePrimaireResponsePage)
                .message("FormeEnergiePrimaire list retrieved successfully!")
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
                .message("FormeEnergiePrimaire counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<FormeEnergiePrimaireResponse> updateOne(@RequestBody FormeEnergiePrimaireRequest dto){
        Instant startProcessing = Instant.now();
        FormeEnergiePrimaireResponse formeEnergiePrimaireResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<FormeEnergiePrimaireResponse>builder()
                .data(formeEnergiePrimaireResponse)
                .message("FormeEnergiePrimaire updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<FormeEnergiePrimaireResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<FormeEnergiePrimaireResponse>builder()
                .message("FormeEnergiePrimaire deleted successfully!")
                .build();
    }
}
