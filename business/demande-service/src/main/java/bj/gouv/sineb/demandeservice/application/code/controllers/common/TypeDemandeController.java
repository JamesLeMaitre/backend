package bj.gouv.sineb.demandeservice.application.code.controllers.common;

import bj.gouv.sineb.common.dto.response.BaseResponse;
import bj.gouv.sineb.demandeservice.application.code.service.common.TypeDemandeService;
import bj.gouv.sineb.demandeservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.demandeservice.common.dto.request.common.TypeDemandeRequest;
import bj.gouv.sineb.demandeservice.common.dto.response.common.TypeDemandeResponse;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/demande/type_demande/")
@RequiredArgsConstructor
public class TypeDemandeController {

    private final ApplicationCommonTask applicationCommonTask;

    private final TypeDemandeService service;

    @PostMapping("save")
    public BaseResponse<TypeDemandeResponse> save(@RequestBody TypeDemandeRequest request) {
        Instant startProcessing = Instant.now();
        TypeDemandeResponse typeDemandeResponse = service.save(request);
        applicationCommonTask.logThisEvent(request.toString(), startProcessing);
        return BaseResponse.<TypeDemandeResponse>builder()
                .data(typeDemandeResponse)
                .status(200)
                .message("TypeDemande created successfully!")
                .build();
    }

    @PutMapping("update/{trackingId}")
    public BaseResponse<TypeDemandeResponse> update(@RequestBody TypeDemandeRequest request, @PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        TypeDemandeResponse typeDemandeResponse = service.update(request, trackingId);
        applicationCommonTask.logThisEvent(request.toString(), startProcessing);
        return BaseResponse.<TypeDemandeResponse>builder()
                .data(typeDemandeResponse)
                .status(200)
                .message("TypeDemande updated successfully!")
                .build();
    }

    @DeleteMapping("delete/{trackingId}")
    public BaseResponse<TypeDemandeResponse> delete(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        TypeDemandeResponse typeDemandeResponse = service.delete(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<TypeDemandeResponse>builder()
                .data(typeDemandeResponse)
                .status(200)
                .message("TypeDemande deleted successfully!")
                .build();
    }

    @GetMapping("{trackingId}")
    public BaseResponse<TypeDemandeResponse> getOne(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        TypeDemandeResponse typeDemandeResponse = service.getOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<TypeDemandeResponse>builder()
                .data(typeDemandeResponse)
                .status(200)
                .message("TypeDemande deleted successfully!")
                .build();
    }

    @GetMapping("all")
    public BaseResponse<Page<TypeDemandeResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<TypeDemandeResponse> typeDemandeResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<TypeDemandeResponse>>builder()
                .data(typeDemandeResponsePage)
                .status(200)
                .message("TypeDemande list retrieved successfully!")
                .build();
    }


}
