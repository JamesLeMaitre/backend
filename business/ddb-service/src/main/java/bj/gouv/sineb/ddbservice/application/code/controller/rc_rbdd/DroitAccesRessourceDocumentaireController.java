package bj.gouv.sineb.ddbservice.application.code.controller.rc_rbdd;

import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.rc_rbdd.DroitAccesRessourceDocumentaireResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.rc_rbdd.DroitAccesRessourceDocumentaireRequest;
import bj.gouv.sineb.ddbservice.application.code.service.rc_rbdd.DroitAccesRessourceDocumentaireService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/rc-rbdd/droit-acces-ressource-documentaire")
public class DroitAccesRessourceDocumentaireController {

    private final ApplicationCommonTask applicationCommonTask;
    private final DroitAccesRessourceDocumentaireService service;


    public DroitAccesRessourceDocumentaireController(ApplicationCommonTask applicationCommonTask, DroitAccesRessourceDocumentaireService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<DroitAccesRessourceDocumentaireResponse> save(@RequestBody DroitAccesRessourceDocumentaireRequest dto) {
        Instant startProcessing = Instant.now();
        DroitAccesRessourceDocumentaireResponse droitAccesRessourceDocumentaireResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<DroitAccesRessourceDocumentaireResponse>builder()
                .data(droitAccesRessourceDocumentaireResponse)
                .message("DroitAccesRessourceDocumentaire created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<DroitAccesRessourceDocumentaireResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        DroitAccesRessourceDocumentaireResponse droitAccesRessourceDocumentaireResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<DroitAccesRessourceDocumentaireResponse>builder()
                .data(droitAccesRessourceDocumentaireResponse)
                .message("DroitAccesRessourceDocumentaire retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<DroitAccesRessourceDocumentaireResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<DroitAccesRessourceDocumentaireResponse> droitAccesRessourceDocumentaireResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<DroitAccesRessourceDocumentaireResponse>>builder()
                .data(droitAccesRessourceDocumentaireResponsePage)
                .message("DroitAccesRessourceDocumentaire list retrieved successfully!")
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
                .message("DroitAccesRessourceDocumentaire counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<DroitAccesRessourceDocumentaireResponse> updateOne(@RequestBody DroitAccesRessourceDocumentaireRequest dto){
        Instant startProcessing = Instant.now();
        DroitAccesRessourceDocumentaireResponse droitAccesRessourceDocumentaireResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<DroitAccesRessourceDocumentaireResponse>builder()
                .data(droitAccesRessourceDocumentaireResponse)
                .message("DroitAccesRessourceDocumentaire updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<DroitAccesRessourceDocumentaireResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<DroitAccesRessourceDocumentaireResponse>builder()
                .message("DroitAccesRessourceDocumentaire deleted successfully!")
                .build();
    }
}
