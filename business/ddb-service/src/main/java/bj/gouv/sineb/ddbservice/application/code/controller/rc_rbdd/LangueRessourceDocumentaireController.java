package bj.gouv.sineb.ddbservice.application.code.controller.rc_rbdd;


import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.rc_rbdd.LangueRessourceDocumentaireResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.rc_rbdd.LangueRessourceDocumentaireRequest;
import bj.gouv.sineb.ddbservice.application.code.service.rc_rbdd.LangueRessourceDocumentaireService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/rc-rbdd/langue-ressource-documentaire")
public class LangueRessourceDocumentaireController {

    private final ApplicationCommonTask applicationCommonTask;
    private final LangueRessourceDocumentaireService service;


    public LangueRessourceDocumentaireController(ApplicationCommonTask applicationCommonTask, LangueRessourceDocumentaireService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<LangueRessourceDocumentaireResponse> save(@RequestBody LangueRessourceDocumentaireRequest dto) {
        Instant startProcessing = Instant.now();
        LangueRessourceDocumentaireResponse langueRessourceDocumentaireResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<LangueRessourceDocumentaireResponse>builder()
                .data(langueRessourceDocumentaireResponse)
                .message("LangueRessourceDocumentaire created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<LangueRessourceDocumentaireResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        LangueRessourceDocumentaireResponse langueRessourceDocumentaireResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<LangueRessourceDocumentaireResponse>builder()
                .data(langueRessourceDocumentaireResponse)
                .message("LangueRessourceDocumentaire retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<LangueRessourceDocumentaireResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<LangueRessourceDocumentaireResponse> langueRessourceDocumentaireResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<LangueRessourceDocumentaireResponse>>builder()
                .data(langueRessourceDocumentaireResponsePage)
                .message("LangueRessourceDocumentaire list retrieved successfully!")
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
                .message("LangueRessourceDocumentaire counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<LangueRessourceDocumentaireResponse> updateOne(@RequestBody LangueRessourceDocumentaireRequest dto){
        Instant startProcessing = Instant.now();
        LangueRessourceDocumentaireResponse langueRessourceDocumentaireResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<LangueRessourceDocumentaireResponse>builder()
                .data(langueRessourceDocumentaireResponse)
                .message("LangueRessourceDocumentaire updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<LangueRessourceDocumentaireResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<LangueRessourceDocumentaireResponse>builder()
                .message("LangueRessourceDocumentaire deleted successfully!")
                .build();
    }
}
