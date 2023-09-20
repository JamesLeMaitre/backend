package bj.gouv.sineb.demandeservice.application.code.controllers.common;


import bj.gouv.sineb.common.dto.response.BaseResponse;
import bj.gouv.sineb.demandeservice.application.code.service.common.CiviliteService;
import bj.gouv.sineb.demandeservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.demandeservice.common.dto.request.common.CiviliteRequest;
import bj.gouv.sineb.demandeservice.common.dto.response.common.CiviliteResponse;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/demande/civilite/")
@RequiredArgsConstructor
public class CiviliteController {

    private final ApplicationCommonTask applicationCommonTask;

    private final CiviliteService service;

    @PostMapping("save")
    public BaseResponse<CiviliteResponse> save(@RequestBody CiviliteRequest request) {
        Instant startProcessing = Instant.now();
        CiviliteResponse civiliteResponse = service.save(request);
        applicationCommonTask.logThisEvent(request.toString(), startProcessing);
        return BaseResponse.<CiviliteResponse>builder()
                .data(civiliteResponse)
                .status(200)
                .message("Civilite created successfully!")
                .build();
    }

    @PutMapping("update/{trackingId}")
    public BaseResponse<CiviliteResponse> update(@RequestBody CiviliteRequest request, @PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        CiviliteResponse civiliteResponse = service.update(request, trackingId);
        applicationCommonTask.logThisEvent(request.toString(), startProcessing);
        return BaseResponse.<CiviliteResponse>builder()
                .data(civiliteResponse)
                .status(200)
                .message("Civilite updated successfully!")
                .build();
    }

    @DeleteMapping("delete/{trackingId}")
    public BaseResponse<CiviliteResponse> delete(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        CiviliteResponse civiliteResponse = service.delete(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<CiviliteResponse>builder()
                .data(civiliteResponse)
                .status(200)
                .message("Civilite deleted successfully!")
                .build();
    }

    @GetMapping("{trackingId}")
    public BaseResponse<CiviliteResponse> getOne(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        CiviliteResponse civiliteResponse = service.getOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<CiviliteResponse>builder()
                .data(civiliteResponse)
                .status(200)
                .message("Civilite deleted successfully!")
                .build();
    }

    @GetMapping("all")
    public BaseResponse<Page<CiviliteResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<CiviliteResponse> civiliteResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<CiviliteResponse>>builder()
                .data(civiliteResponsePage)
                .status(200)
                .message("Civilite list retrieved successfully!")
                .build();
    }

}
