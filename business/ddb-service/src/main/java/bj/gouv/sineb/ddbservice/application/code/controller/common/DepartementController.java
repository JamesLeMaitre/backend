package bj.gouv.sineb.ddbservice.application.code.controller.common;

import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.DepartementResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.DepartementRequest;
import bj.gouv.sineb.ddbservice.application.code.service.common.DepartementService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/common/departement")
public class DepartementController {

    private final ApplicationCommonTask applicationCommonTask;
    private final DepartementService service;


    public DepartementController(ApplicationCommonTask applicationCommonTask, DepartementService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<DepartementResponse> save(@RequestBody DepartementRequest dto) {
        Instant startProcessing = Instant.now();
        DepartementResponse departementResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<DepartementResponse>builder()
                .data(departementResponse)
                .message("Departement created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<DepartementResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        DepartementResponse departementResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<DepartementResponse>builder()
                .data(departementResponse)
                .message("Departement retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<DepartementResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<DepartementResponse> departementResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<DepartementResponse>>builder()
                .data(departementResponsePage)
                .message("Departement list retrieved successfully!")
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
                .message("Departement counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<DepartementResponse> updateOne(@RequestBody DepartementRequest dto){
        Instant startProcessing = Instant.now();
        DepartementResponse departementResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<DepartementResponse>builder()
                .data(departementResponse)
                .message("Departement updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<DepartementResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<DepartementResponse>builder()
                .message("Departement deleted successfully!")
                .build();
    }
}
