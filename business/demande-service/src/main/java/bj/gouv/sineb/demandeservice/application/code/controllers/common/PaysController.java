package bj.gouv.sineb.demandeservice.application.code.controllers.common;

import bj.gouv.sineb.common.dto.response.BaseResponse;
import bj.gouv.sineb.demandeservice.application.code.service.common.PaysService;
import bj.gouv.sineb.demandeservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.demandeservice.common.dto.request.common.PaysRequest;
import bj.gouv.sineb.demandeservice.common.dto.response.common.PaysResponse;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/demande/pays/")
@RequiredArgsConstructor
public class PaysController {

    private final ApplicationCommonTask applicationCommonTask;

    private final PaysService service;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("save")
    public BaseResponse<PaysResponse> save(@RequestBody PaysRequest request) {
        Instant startProcessing = Instant.now();
        PaysResponse paysResponse = service.save(request);
        applicationCommonTask.logThisEvent(request.toString(), startProcessing);
        return BaseResponse.<PaysResponse>builder()
                .data(paysResponse)
                .message("Pays created successfully!")
                .status(200)
                .build();
    }

    @PutMapping("update/{trackingId}")
    public BaseResponse<PaysResponse> update(@RequestBody PaysRequest request, @PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        PaysResponse paysResponse = service.update(request, trackingId);
        applicationCommonTask.logThisEvent(request.toString(), startProcessing);
        return BaseResponse.<PaysResponse>builder()
                .data(paysResponse)
                .status(200)
                .message("Pays updated successfully!")
                .build();
    }

    @DeleteMapping("delete/{trackingId}")
    public BaseResponse<PaysResponse> delete(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        PaysResponse paysResponse = service.delete(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<PaysResponse>builder()
                .data(paysResponse)
                .status(200)
                .message("Pays deleted successfully!")
                .build();
    }

    @GetMapping("{trackingId}")
    public BaseResponse<PaysResponse> getOne(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        PaysResponse paysResponse = service.getOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<PaysResponse>builder()
                .data(paysResponse)
                .status(200)
                .message("Pays deleted successfully!")
                .build();
    }

    @GetMapping("all")
    public BaseResponse<Page<PaysResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<PaysResponse> paysResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<PaysResponse>>builder()
                .data(paysResponsePage)
                .status(200)
                .message("Pays list retrieved successfully!")
                .build();
    }
    
    
}
