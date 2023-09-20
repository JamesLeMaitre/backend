package bj.gouv.sineb.ddbservice.application.code.controller.rc_rbdd;

import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.rc_rbdd.TypeRessourceDocumentaireResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.rc_rbdd.TypeRessourceDocumentaireRequest;
import bj.gouv.sineb.ddbservice.application.code.service.rc_rbdd.TypeRessourceDocumentaireService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/rc-rbdd/type-ressource-documentaire")
public class TypeRessourceDocumentaireController {

    private final ApplicationCommonTask applicationCommonTask;
    private final TypeRessourceDocumentaireService service;


    public TypeRessourceDocumentaireController(ApplicationCommonTask applicationCommonTask, TypeRessourceDocumentaireService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<TypeRessourceDocumentaireResponse> save(@RequestBody TypeRessourceDocumentaireRequest dto) {
        Instant startProcessing = Instant.now();
        TypeRessourceDocumentaireResponse typeRessourceDocumentaireResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<TypeRessourceDocumentaireResponse>builder()
                .data(typeRessourceDocumentaireResponse)
                .message("TypeRessourceDocumentaire created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<TypeRessourceDocumentaireResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        TypeRessourceDocumentaireResponse typeRessourceDocumentaireResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<TypeRessourceDocumentaireResponse>builder()
                .data(typeRessourceDocumentaireResponse)
                .message("TypeRessourceDocumentaire retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<TypeRessourceDocumentaireResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<TypeRessourceDocumentaireResponse> typeRessourceDocumentaireResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<TypeRessourceDocumentaireResponse>>builder()
                .data(typeRessourceDocumentaireResponsePage)
                .message("TypeRessourceDocumentaire list retrieved successfully!")
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
                .message("TypeRessourceDocumentaire counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<TypeRessourceDocumentaireResponse> updateOne(@RequestBody TypeRessourceDocumentaireRequest dto){
        Instant startProcessing = Instant.now();
        TypeRessourceDocumentaireResponse typeRessourceDocumentaireResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<TypeRessourceDocumentaireResponse>builder()
                .data(typeRessourceDocumentaireResponse)
                .message("TypeRessourceDocumentaire updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<TypeRessourceDocumentaireResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<TypeRessourceDocumentaireResponse>builder()
                .message("TypeRessourceDocumentaire deleted successfully!")
                .build();
    }
}
