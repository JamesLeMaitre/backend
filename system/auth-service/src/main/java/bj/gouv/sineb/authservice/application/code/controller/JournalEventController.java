package bj.gouv.sineb.authservice.application.code.controller;

import bj.gouv.sineb.authservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.authservice.application.code.dto.response.BaseResponse;
import bj.gouv.sineb.authservice.application.code.dto.response.JournalEventResponse;
import bj.gouv.sineb.authservice.application.code.service.JournalEventService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/auth/journal-events")
public class JournalEventController {

    private final ApplicationCommonTask applicationCommonTask;
    private final JournalEventService service;
    


    public JournalEventController(ApplicationCommonTask applicationCommonTask, JournalEventService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }


    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<JournalEventResponse> getOneByTrackingId(@PathVariable UUID trackingId){
        return BaseResponse.<JournalEventResponse>builder()
                .data(service.getOneByTrackingId(trackingId))
                .message("JournalEvent created successfully!")
                .build();
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Page<JournalEventResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size
    ){
        return BaseResponse.<Page<JournalEventResponse>>builder()
                .data(service.getAll(page, size))
                .message("JournalEvent list retrieved successfully!")
                .build();
    }

    @GetMapping("/count")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Long> count(){
        return BaseResponse.<Long>builder()
                .data(service.count())
                .message("JournalEvent counted successfully!")
                .build();
    }
}
