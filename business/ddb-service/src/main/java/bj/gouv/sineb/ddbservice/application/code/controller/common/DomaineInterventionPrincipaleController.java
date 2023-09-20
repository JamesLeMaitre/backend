package bj.gouv.sineb.ddbservice.application.code.controller.common;



import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.DomaineInterventionPrincipaleResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.DomaineInterventionPrincipaleRequest;
import bj.gouv.sineb.ddbservice.application.code.service.common.DomaineInterventionPrincipaleService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/common/domaine-intervention-principale")
public class DomaineInterventionPrincipaleController {

    private final ApplicationCommonTask applicationCommonTask;
    private final DomaineInterventionPrincipaleService service;


    public DomaineInterventionPrincipaleController(ApplicationCommonTask applicationCommonTask, DomaineInterventionPrincipaleService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<DomaineInterventionPrincipaleResponse> save(@RequestBody DomaineInterventionPrincipaleRequest dto) {
        Instant startProcessing = Instant.now();
        DomaineInterventionPrincipaleResponse domaineInterventionPrincipaleResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<DomaineInterventionPrincipaleResponse>builder()
                .data(domaineInterventionPrincipaleResponse)
                .message("DomaineInterventionPrincipale created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<DomaineInterventionPrincipaleResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        DomaineInterventionPrincipaleResponse domaineInterventionPrincipaleResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<DomaineInterventionPrincipaleResponse>builder()
                .data(domaineInterventionPrincipaleResponse)
                .message("DomaineInterventionPrincipale retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<DomaineInterventionPrincipaleResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<DomaineInterventionPrincipaleResponse> domaineInterventionPrincipaleResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<DomaineInterventionPrincipaleResponse>>builder()
                .data(domaineInterventionPrincipaleResponsePage)
                .message("DomaineInterventionPrincipale list retrieved successfully!")
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
                .message("DomaineInterventionPrincipale counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<DomaineInterventionPrincipaleResponse> updateOne(@RequestBody DomaineInterventionPrincipaleRequest dto){
        Instant startProcessing = Instant.now();
        DomaineInterventionPrincipaleResponse domaineInterventionPrincipaleResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<DomaineInterventionPrincipaleResponse>builder()
                .data(domaineInterventionPrincipaleResponse)
                .message("DomaineInterventionPrincipale updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<DomaineInterventionPrincipaleResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<DomaineInterventionPrincipaleResponse>builder()
                .message("DomaineInterventionPrincipale deleted successfully!")
                .build();
    }
    
}
