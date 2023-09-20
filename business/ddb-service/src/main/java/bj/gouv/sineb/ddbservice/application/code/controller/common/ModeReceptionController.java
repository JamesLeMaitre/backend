package bj.gouv.sineb.ddbservice.application.code.controller.common;

import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.ModeReceptionResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.ModeReceptionRequest;
import bj.gouv.sineb.ddbservice.application.code.service.common.ModeReceptionService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/common/mode-reception")
public class ModeReceptionController {

    private final ApplicationCommonTask applicationCommonTask;
    private final ModeReceptionService service;


    public ModeReceptionController(ApplicationCommonTask applicationCommonTask, ModeReceptionService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<ModeReceptionResponse> save(@RequestBody ModeReceptionRequest dto) {
        Instant startProcessing = Instant.now();
        ModeReceptionResponse modeReceptionResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<ModeReceptionResponse>builder()
                .data(modeReceptionResponse)
                .message("ModeReception created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ModeReceptionResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        ModeReceptionResponse modeReceptionResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<ModeReceptionResponse>builder()
                .data(modeReceptionResponse)
                .message("ModeReception retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<ModeReceptionResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<ModeReceptionResponse> modeReceptionResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<ModeReceptionResponse>>builder()
                .data(modeReceptionResponsePage)
                .message("ModeReception list retrieved successfully!")
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
                .message("ModeReception counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ModeReceptionResponse> updateOne(@RequestBody ModeReceptionRequest dto){
        Instant startProcessing = Instant.now();
        ModeReceptionResponse modeReceptionResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<ModeReceptionResponse>builder()
                .data(modeReceptionResponse)
                .message("ModeReception updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ModeReceptionResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<ModeReceptionResponse>builder()
                .message("ModeReception deleted successfully!")
                .build();
    }
}
