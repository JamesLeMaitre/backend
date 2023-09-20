package bj.gouv.sineb.ddbservice.application.code.controller.common;


import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.SousCategoryEltResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.SousCategoryEltRequest;
import bj.gouv.sineb.ddbservice.application.code.service.common.SousCategoryEltService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/common/sous-category-elt")
public class SousCategoryEltController {

    private final ApplicationCommonTask applicationCommonTask;
    private final SousCategoryEltService service;


    public SousCategoryEltController(ApplicationCommonTask applicationCommonTask, SousCategoryEltService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<SousCategoryEltResponse> save(@RequestBody SousCategoryEltRequest dto) {
        Instant startProcessing = Instant.now();
        SousCategoryEltResponse sousCategoryEltResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<SousCategoryEltResponse>builder()
                .data(sousCategoryEltResponse)
                .message("SousCategoryElt created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<SousCategoryEltResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        SousCategoryEltResponse sousCategoryEltResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<SousCategoryEltResponse>builder()
                .data(sousCategoryEltResponse)
                .message("SousCategoryElt retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<SousCategoryEltResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<SousCategoryEltResponse> sousCategoryEltResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<SousCategoryEltResponse>>builder()
                .data(sousCategoryEltResponsePage)
                .message("SousCategoryElt list retrieved successfully!")
                .build();
    }

    @GetMapping("/by-category-elt/{categoryEltTrackingId}")
    public BaseResponse<Page<SousCategoryEltResponse>> getAll(
            @PathVariable UUID categoryEltTrackingId,
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<SousCategoryEltResponse> sousCategoryEltResponsePage = service.getAllByCategoryId(categoryEltTrackingId, page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<SousCategoryEltResponse>>builder()
                .data(sousCategoryEltResponsePage)
                .message("SousCategoryElt list by category element retrieved successfully!")
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
                .message("SousCategoryElt counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<SousCategoryEltResponse> updateOne(@RequestBody SousCategoryEltRequest dto){
        Instant startProcessing = Instant.now();
        SousCategoryEltResponse sousCategoryEltResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<SousCategoryEltResponse>builder()
                .data(sousCategoryEltResponse)
                .message("SousCategoryElt updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<SousCategoryEltResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<SousCategoryEltResponse>builder()
                .message("SousCategoryElt deleted successfully!")
                .build();
    }
}
