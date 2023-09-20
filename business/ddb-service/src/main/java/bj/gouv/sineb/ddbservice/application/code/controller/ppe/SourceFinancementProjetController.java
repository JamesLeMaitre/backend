package bj.gouv.sineb.ddbservice.application.code.controller.ppe;


import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.ppe.SourceFinancementProjetResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.ppe.SourceFinancementProjetRequest;
import bj.gouv.sineb.ddbservice.application.code.service.ppe.SourceFinancementProjetService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/ppe/source-financement-projet")
public class SourceFinancementProjetController {

    private final ApplicationCommonTask applicationCommonTask;
    private final SourceFinancementProjetService service;


    public SourceFinancementProjetController(ApplicationCommonTask applicationCommonTask, SourceFinancementProjetService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<SourceFinancementProjetResponse> save(@RequestBody SourceFinancementProjetRequest dto) {
        Instant startProcessing = Instant.now();
        SourceFinancementProjetResponse sourceFinancementProjetResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<SourceFinancementProjetResponse>builder()
                .data(sourceFinancementProjetResponse)
                .message("SourceFinancementProjet created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<SourceFinancementProjetResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        SourceFinancementProjetResponse sourceFinancementProjetResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<SourceFinancementProjetResponse>builder()
                .data(sourceFinancementProjetResponse)
                .message("SourceFinancementProjet retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<SourceFinancementProjetResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<SourceFinancementProjetResponse> sourceFinancementProjetResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<SourceFinancementProjetResponse>>builder()
                .data(sourceFinancementProjetResponsePage)
                .message("SourceFinancementProjet list retrieved successfully!")
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
                .message("SourceFinancementProjet counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<SourceFinancementProjetResponse> updateOne(@RequestBody SourceFinancementProjetRequest dto){
        Instant startProcessing = Instant.now();
        SourceFinancementProjetResponse sourceFinancementProjetResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<SourceFinancementProjetResponse>builder()
                .data(sourceFinancementProjetResponse)
                .message("SourceFinancementProjet updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<SourceFinancementProjetResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<SourceFinancementProjetResponse>builder()
                .message("SourceFinancementProjet deleted successfully!")
                .build();
    }
}
