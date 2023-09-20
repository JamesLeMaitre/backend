package bj.gouv.sineb.ddbservice.application.code.controller.aae;

import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.RoleActeurResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.RoleActeurRequest;
import bj.gouv.sineb.ddbservice.application.code.service.aae.RoleActeurService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/aae/role-acteur")
public class RoleActeurController {

    private final ApplicationCommonTask applicationCommonTask;
    private final RoleActeurService service;


    public RoleActeurController(ApplicationCommonTask applicationCommonTask, RoleActeurService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<RoleActeurResponse> save(@RequestBody RoleActeurRequest dto) {
        Instant startProcessing = Instant.now();
        RoleActeurResponse roleActeurResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<RoleActeurResponse>builder()
                .data(roleActeurResponse)
                .message("RoleActeur created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<RoleActeurResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        RoleActeurResponse roleActeurResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<RoleActeurResponse>builder()
                .data(roleActeurResponse)
                .message("RoleActeur retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<RoleActeurResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<RoleActeurResponse> roleActeurResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<RoleActeurResponse>>builder()
                .data(roleActeurResponsePage)
                .message("RoleActeur list retrieved successfully!")
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
                .message("RoleActeur counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<RoleActeurResponse> updateOne(@RequestBody RoleActeurRequest dto){
        Instant startProcessing = Instant.now();
        RoleActeurResponse roleActeurResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<RoleActeurResponse>builder()
                .data(roleActeurResponse)
                .message("RoleActeur updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<RoleActeurResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<RoleActeurResponse>builder()
                .message("RoleActeur deleted successfully!")
                .build();
    }
}
