package bj.gouv.sineb.ddbservice.application.code.controller.rc_rbdd;

import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.rc_rbdd.PorteeRessourceDocumentaireResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.rc_rbdd.PorteeRessourceDocumentaireRequest;
import bj.gouv.sineb.ddbservice.application.code.service.rc_rbdd.PorteeRessourceDocumentaireService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/rc-rbdd/portee-ressource-documentaire")
public class PorteeRessourceDocumentaireController {

    private final ApplicationCommonTask applicationCommonTask;
    private final PorteeRessourceDocumentaireService service;


    public PorteeRessourceDocumentaireController(ApplicationCommonTask applicationCommonTask, PorteeRessourceDocumentaireService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<PorteeRessourceDocumentaireResponse> save(@RequestBody PorteeRessourceDocumentaireRequest dto) {
        Instant startProcessing = Instant.now();
        PorteeRessourceDocumentaireResponse porteeRessourceDocumentaireResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<PorteeRessourceDocumentaireResponse>builder()
                .data(porteeRessourceDocumentaireResponse)
                .message("PorteeRessourceDocumentaire created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<PorteeRessourceDocumentaireResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        PorteeRessourceDocumentaireResponse porteeRessourceDocumentaireResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<PorteeRessourceDocumentaireResponse>builder()
                .data(porteeRessourceDocumentaireResponse)
                .message("PorteeRessourceDocumentaire retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<PorteeRessourceDocumentaireResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<PorteeRessourceDocumentaireResponse> porteeRessourceDocumentaireResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<PorteeRessourceDocumentaireResponse>>builder()
                .data(porteeRessourceDocumentaireResponsePage)
                .message("PorteeRessourceDocumentaire list retrieved successfully!")
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
                .message("PorteeRessourceDocumentaire counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<PorteeRessourceDocumentaireResponse> updateOne(@RequestBody PorteeRessourceDocumentaireRequest dto){
        Instant startProcessing = Instant.now();
        PorteeRessourceDocumentaireResponse porteeRessourceDocumentaireResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<PorteeRessourceDocumentaireResponse>builder()
                .data(porteeRessourceDocumentaireResponse)
                .message("PorteeRessourceDocumentaire updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<PorteeRessourceDocumentaireResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<PorteeRessourceDocumentaireResponse>builder()
                .message("PorteeRessourceDocumentaire deleted successfully!")
                .build();
    }
}
