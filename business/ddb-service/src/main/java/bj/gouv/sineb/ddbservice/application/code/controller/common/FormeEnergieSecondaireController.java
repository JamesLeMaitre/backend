package bj.gouv.sineb.ddbservice.application.code.controller.common;

import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.FormeEnergieSecondaireResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.FormeEnergieSecondaireRequest;
import bj.gouv.sineb.ddbservice.application.code.service.common.FormeEnergieSecondaireService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/common/forme-energie-secondaire")
public class FormeEnergieSecondaireController {
    private final ApplicationCommonTask applicationCommonTask;
    private final FormeEnergieSecondaireService service;


    public FormeEnergieSecondaireController(ApplicationCommonTask applicationCommonTask, FormeEnergieSecondaireService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<FormeEnergieSecondaireResponse> save(@RequestBody FormeEnergieSecondaireRequest dto) {
        Instant startProcessing = Instant.now();
        FormeEnergieSecondaireResponse formeEnergieSecondaireResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<FormeEnergieSecondaireResponse>builder()
                .data(formeEnergieSecondaireResponse)
                .message("FormeEnergieSecondaire created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<FormeEnergieSecondaireResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        FormeEnergieSecondaireResponse formeEnergieSecondaireResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<FormeEnergieSecondaireResponse>builder()
                .data(formeEnergieSecondaireResponse)
                .message("FormeEnergieSecondaire retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<FormeEnergieSecondaireResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<FormeEnergieSecondaireResponse> formeEnergieSecondaireResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<FormeEnergieSecondaireResponse>>builder()
                .data(formeEnergieSecondaireResponsePage)
                .message("FormeEnergieSecondaire list retrieved successfully!")
                .build();
    }

    @GetMapping("/by-forme-energie-primaire/{fepTrackingId}")
    public BaseResponse<Page<FormeEnergieSecondaireResponse>> getAll(
            @PathVariable UUID fepTrackingId,
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<FormeEnergieSecondaireResponse> formeEnergieSecondaireResponsePage = service.getAllByFormeEnergiePrimaireId(fepTrackingId, page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<FormeEnergieSecondaireResponse>>builder()
                .data(formeEnergieSecondaireResponsePage)
                .message("FormeEnergieSecondaire list by forme energie primaire retrieved successfully!")
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
                .message("FormeEnergieSecondaire counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<FormeEnergieSecondaireResponse> updateOne(@RequestBody FormeEnergieSecondaireRequest dto){
        Instant startProcessing = Instant.now();
        FormeEnergieSecondaireResponse formeEnergieSecondaireResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<FormeEnergieSecondaireResponse>builder()
                .data(formeEnergieSecondaireResponse)
                .message("FormeEnergieSecondaire updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<FormeEnergieSecondaireResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<FormeEnergieSecondaireResponse>builder()
                .message("FormeEnergieSecondaire deleted successfully!")
                .build();
    }
}
