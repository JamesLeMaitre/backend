package bj.gouv.sineb.ddbservice.application.code.controller.common;

import bj.gouv.sineb.common.dto.response.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.TypeCompteRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.TypeCompteResponse;
import bj.gouv.sineb.ddbservice.application.code.service.common.TypeCompteService;
import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/demande/type_compte/")
@RequiredArgsConstructor
public class TypeCompteController {


    private final ApplicationCommonTask applicationCommonTask;

    private final TypeCompteService service;

    @PostMapping("save")
    public BaseResponse<TypeCompteResponse> save(@RequestBody TypeCompteRequest request) {
        Instant startProcessing = Instant.now();
        TypeCompteResponse typeCompteResponse = service.save(request);
        applicationCommonTask.logThisEvent(request.toString(), startProcessing);
        return BaseResponse.<TypeCompteResponse>builder()
                .data(typeCompteResponse)
                .status(200)
                .message("TypeCompte created successfully!")
                .build();
    }

    @PutMapping("update/{trackingId}")
    public BaseResponse<TypeCompteResponse> update(@RequestBody TypeCompteRequest request, @PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        TypeCompteResponse typeCompteResponse = service.update(request, trackingId);
        applicationCommonTask.logThisEvent(request.toString(), startProcessing);
        return BaseResponse.<TypeCompteResponse>builder()
                .data(typeCompteResponse)
                .status(200)
                .message("TypeCompte updated successfully!")
                .build();
    }

    @DeleteMapping("delete/{trackingId}")
    public BaseResponse<TypeCompteResponse> delete(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        TypeCompteResponse typeCompteResponse = service.delete(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<TypeCompteResponse>builder()
                .data(typeCompteResponse)
                .status(200)
                .message("TypeCompte deleted successfully!")
                .build();
    }

    @GetMapping("{trackingId}")
    public BaseResponse<TypeCompteResponse> getOne(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        TypeCompteResponse typeCompteResponse = service.getOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<TypeCompteResponse>builder()
                .data(typeCompteResponse)
                .status(200)
                .message("TypeCompte deleted successfully!")
                .build();
    }

    @GetMapping("all")
    public BaseResponse<Page<TypeCompteResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<TypeCompteResponse> typeCompteResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<TypeCompteResponse>>builder()
                .data(typeCompteResponsePage)
                .status(200)
                .message("TypeCompte list retrieved successfully!")
                .build();
    }


}
