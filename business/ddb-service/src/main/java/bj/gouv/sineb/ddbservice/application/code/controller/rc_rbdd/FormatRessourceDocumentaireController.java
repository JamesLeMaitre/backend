package bj.gouv.sineb.ddbservice.application.code.controller.rc_rbdd;

import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.rc_rbdd.FormatRessourceDocumentaireResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.rc_rbdd.FormatRessourceDocumentaireRequest;
import bj.gouv.sineb.ddbservice.application.code.service.rc_rbdd.FormatRessourceDocumentaireService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/rc-rbdd/format-ressource-documentaire")
public class FormatRessourceDocumentaireController {

    private final ApplicationCommonTask applicationCommonTask;
    private final FormatRessourceDocumentaireService service;


    public FormatRessourceDocumentaireController(ApplicationCommonTask applicationCommonTask, FormatRessourceDocumentaireService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<FormatRessourceDocumentaireResponse> save(@RequestBody FormatRessourceDocumentaireRequest dto) {
        Instant startProcessing = Instant.now();
        FormatRessourceDocumentaireResponse formatRessourceDocumentaireResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<FormatRessourceDocumentaireResponse>builder()
                .data(formatRessourceDocumentaireResponse)
                .message("FormatRessourceDocumentaire created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<FormatRessourceDocumentaireResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        FormatRessourceDocumentaireResponse formatRessourceDocumentaireResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<FormatRessourceDocumentaireResponse>builder()
                .data(formatRessourceDocumentaireResponse)
                .message("FormatRessourceDocumentaire retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<FormatRessourceDocumentaireResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<FormatRessourceDocumentaireResponse> formatRessourceDocumentaireResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<FormatRessourceDocumentaireResponse>>builder()
                .data(formatRessourceDocumentaireResponsePage)
                .message("FormatRessourceDocumentaire list retrieved successfully!")
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
                .message("FormatRessourceDocumentaire counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<FormatRessourceDocumentaireResponse> updateOne(@RequestBody FormatRessourceDocumentaireRequest dto){
        Instant startProcessing = Instant.now();
        FormatRessourceDocumentaireResponse formatRessourceDocumentaireResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<FormatRessourceDocumentaireResponse>builder()
                .data(formatRessourceDocumentaireResponse)
                .message("FormatRessourceDocumentaire updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<FormatRessourceDocumentaireResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<FormatRessourceDocumentaireResponse>builder()
                .message("FormatRessourceDocumentaire deleted successfully!")
                .build();
    }
}
