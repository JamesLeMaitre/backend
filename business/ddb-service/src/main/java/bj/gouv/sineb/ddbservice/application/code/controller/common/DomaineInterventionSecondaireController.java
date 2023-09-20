package bj.gouv.sineb.ddbservice.application.code.controller.common;



import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.DomaineInterventionSecondaireResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.DomaineInterventionSecondaireRequest;
import bj.gouv.sineb.ddbservice.application.code.service.common.DomaineInterventionSecondaireService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/common/domaine-intervention-secondaire")
public class DomaineInterventionSecondaireController {

    private final ApplicationCommonTask applicationCommonTask;
    private final DomaineInterventionSecondaireService service;


    public DomaineInterventionSecondaireController(ApplicationCommonTask applicationCommonTask, DomaineInterventionSecondaireService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<DomaineInterventionSecondaireResponse> save(@RequestBody DomaineInterventionSecondaireRequest dto) {
        Instant startProcessing = Instant.now();
        DomaineInterventionSecondaireResponse civiliteResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<DomaineInterventionSecondaireResponse>builder()
                .data(civiliteResponse)
                .message("DomaineInterventionSecondaire created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<DomaineInterventionSecondaireResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        DomaineInterventionSecondaireResponse civiliteResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<DomaineInterventionSecondaireResponse>builder()
                .data(civiliteResponse)
                .message("DomaineInterventionSecondaire retrieved successfully!")
                .build();
    }

    @GetMapping("/by-dip/{dipTrackingId}")
    public BaseResponse<Page<DomaineInterventionSecondaireResponse>> getAll(
            @PathVariable UUID dipTrackingId,
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<DomaineInterventionSecondaireResponse> civiliteResponsePage = service.getAllByDomaineInterventionPrincipaleId(dipTrackingId, page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<DomaineInterventionSecondaireResponse>>builder()
                .data(civiliteResponsePage)
                .message("DomaineInterventionSecondaire list by domaine intervention primaire retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<DomaineInterventionSecondaireResponse>> getAllByDIPrimaire(

            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<DomaineInterventionSecondaireResponse> civiliteResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<DomaineInterventionSecondaireResponse>>builder()
                .data(civiliteResponsePage)
                .message("DomaineInterventionSecondaire list retrieved successfully!")
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
                .message("DomaineInterventionSecondaire counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<DomaineInterventionSecondaireResponse> updateOne(@RequestBody DomaineInterventionSecondaireRequest dto){
        Instant startProcessing = Instant.now();
        DomaineInterventionSecondaireResponse civiliteResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<DomaineInterventionSecondaireResponse>builder()
                .data(civiliteResponse)
                .message("DomaineInterventionSecondaire updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<DomaineInterventionSecondaireResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<DomaineInterventionSecondaireResponse>builder()
                .message("DomaineInterventionSecondaire deleted successfully!")
                .build();
    }
}
