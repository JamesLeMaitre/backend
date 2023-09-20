package bj.gouv.sineb.authservice.application.code.controller;

import bj.gouv.sineb.authservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.authservice.application.code.dto.request.GroupeRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.BaseResponse;
import bj.gouv.sineb.authservice.application.code.dto.response.GroupeResponse;
import bj.gouv.sineb.authservice.application.code.service.GroupeService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/auth/groupes")
public class GroupeController {

    private final ApplicationCommonTask applicationCommonTask;
    private final GroupeService service;


    public GroupeController(ApplicationCommonTask applicationCommonTask, GroupeService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<GroupeResponse> save(@RequestBody GroupeRequest dto) {
        Instant startProcessing = Instant.now();
        GroupeResponse groupeResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<GroupeResponse>builder()
                .data(groupeResponse)
                .message("Groupe created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<GroupeResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        GroupeResponse groupeResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<GroupeResponse>builder()
                .data(groupeResponse)
                .message("Groupe retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<GroupeResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size,
            @RequestParam boolean deleted){
        Instant startProcessing = Instant.now();
        Page<GroupeResponse> groupeResponsePage = service.getAll(page, size, deleted);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<GroupeResponse>>builder()
                .data(groupeResponsePage)
                .message("Groupe list retrieved successfully!")
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
                .message("Groupe counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<GroupeResponse> updateOne(@RequestBody GroupeRequest dto){
        Instant startProcessing = Instant.now();
        GroupeResponse groupeResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<GroupeResponse>builder()
                .data(groupeResponse)
                .message("Groupe updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<GroupeResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<GroupeResponse>builder()
                .message("Groupe deleted successfully!")
                .build();
    }

}
