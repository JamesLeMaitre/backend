package bj.gouv.sineb.ddbservice.application.code.controller.aae;

import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.ProfessionResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.ProfessionRequest;
import bj.gouv.sineb.ddbservice.application.code.service.aae.ProfessionService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/aae/profession")
public class ProfessionController {

    private final ApplicationCommonTask applicationCommonTask;
    private final ProfessionService service;


    public ProfessionController(ApplicationCommonTask applicationCommonTask, ProfessionService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<ProfessionResponse> save(@RequestBody ProfessionRequest dto) {
        Instant startProcessing = Instant.now();
        ProfessionResponse professionResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<ProfessionResponse>builder()
                .data(professionResponse)
                .message("Profession created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ProfessionResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        ProfessionResponse professionResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<ProfessionResponse>builder()
                .data(professionResponse)
                .message("Profession retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<ProfessionResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<ProfessionResponse> professionResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<ProfessionResponse>>builder()
                .data(professionResponsePage)
                .message("Profession list retrieved successfully!")
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
                .message("Profession counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ProfessionResponse> updateOne(@RequestBody ProfessionRequest dto){
        Instant startProcessing = Instant.now();
        ProfessionResponse professionResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<ProfessionResponse>builder()
                .data(professionResponse)
                .message("Profession updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ProfessionResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<ProfessionResponse>builder()
                .message("Profession deleted successfully!")
                .build();
    }
}
