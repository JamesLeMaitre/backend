package bj.gouv.sineb.ddbservice.application.code.controller.rc_rbdd;

import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.rc_rbdd.NatureRessourceDocumentaireResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.rc_rbdd.NatureRessourceDocumentaireRequest;
import bj.gouv.sineb.ddbservice.application.code.service.rc_rbdd.NatureRessourceDocumentaireService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/rc-rbdd/nature-ressource-documentaire")
public class NatureRessourceDocumentaireController {

    private final ApplicationCommonTask applicationCommonTask;
    private final NatureRessourceDocumentaireService service;


    public NatureRessourceDocumentaireController(ApplicationCommonTask applicationCommonTask, NatureRessourceDocumentaireService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<NatureRessourceDocumentaireResponse> save(@RequestBody NatureRessourceDocumentaireRequest dto) {
        Instant startProcessing = Instant.now();
        NatureRessourceDocumentaireResponse natureRessourceDocumentaireResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<NatureRessourceDocumentaireResponse>builder()
                .data(natureRessourceDocumentaireResponse)
                .message("NatureRessourceDocumentaire created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<NatureRessourceDocumentaireResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        NatureRessourceDocumentaireResponse natureRessourceDocumentaireResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<NatureRessourceDocumentaireResponse>builder()
                .data(natureRessourceDocumentaireResponse)
                .message("NatureRessourceDocumentaire retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<NatureRessourceDocumentaireResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<NatureRessourceDocumentaireResponse> natureRessourceDocumentaireResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<NatureRessourceDocumentaireResponse>>builder()
                .data(natureRessourceDocumentaireResponsePage)
                .message("NatureRessourceDocumentaire list retrieved successfully!")
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
                .message("NatureRessourceDocumentaire counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<NatureRessourceDocumentaireResponse> updateOne(@RequestBody NatureRessourceDocumentaireRequest dto){
        Instant startProcessing = Instant.now();
        NatureRessourceDocumentaireResponse natureRessourceDocumentaireResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<NatureRessourceDocumentaireResponse>builder()
                .data(natureRessourceDocumentaireResponse)
                .message("NatureRessourceDocumentaire updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<NatureRessourceDocumentaireResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<NatureRessourceDocumentaireResponse>builder()
                .message("NatureRessourceDocumentaire deleted successfully!")
                .build();
    }
}
