package bj.gouv.sineb.ddbservice.application.code.controller.aae;

import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.TitreActeurResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.TitreActeurRequest;
import bj.gouv.sineb.ddbservice.application.code.service.aae.TitreActeurService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/aae/titre-acteur")
public class TitreActeurController {

    private final ApplicationCommonTask applicationCommonTask;
    private final TitreActeurService service;


    public TitreActeurController(ApplicationCommonTask applicationCommonTask, TitreActeurService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<TitreActeurResponse> save(@RequestBody TitreActeurRequest dto) {
        Instant startProcessing = Instant.now();
        TitreActeurResponse titreActeurResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<TitreActeurResponse>builder()
                .data(titreActeurResponse)
                .message("TitreActeur created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<TitreActeurResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        TitreActeurResponse titreActeurResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<TitreActeurResponse>builder()
                .data(titreActeurResponse)
                .message("TitreActeur retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<TitreActeurResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<TitreActeurResponse> titreActeurResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<TitreActeurResponse>>builder()
                .data(titreActeurResponsePage)
                .message("TitreActeur list retrieved successfully!")
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
                .message("TitreActeur counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<TitreActeurResponse> updateOne(@RequestBody TitreActeurRequest dto){
        Instant startProcessing = Instant.now();
        TitreActeurResponse titreActeurResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<TitreActeurResponse>builder()
                .data(titreActeurResponse)
                .message("TitreActeur updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<TitreActeurResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<TitreActeurResponse>builder()
                .message("TitreActeur deleted successfully!")
                .build();
    }
}
