package bj.gouv.sineb.authservice.application.code.controller;

import bj.gouv.sineb.authservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.authservice.application.code.dto.request.RessourceRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.BaseResponse;
import bj.gouv.sineb.authservice.application.code.dto.response.RessourceResponse;
import bj.gouv.sineb.authservice.application.code.service.RessourceService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/auth/ressources")
public class RessourceController {

    private final ApplicationCommonTask applicationCommonTask;
    private final RessourceService service;

    public RessourceController(ApplicationCommonTask applicationCommonTask, RessourceService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<RessourceResponse> save(@RequestBody RessourceRequest dto){
        Instant startProcessing = Instant.now();
        RessourceResponse ressourceResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<RessourceResponse>builder()
                .data(ressourceResponse)
                .message("Ressource created successfully!")
                .build();
    }

    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<RessourceResponse> getOneByTrackingId(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        RessourceResponse ressourceResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<RessourceResponse>builder()
                .data(ressourceResponse)
                .message("Ressource retrieved successfully!")
                .build();
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Page<RessourceResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size,
            @RequestParam boolean deleted
    ){
        Instant startProcessing = Instant.now();
        Page<RessourceResponse> ressourceResponsePage = service.getAll(page, size, deleted);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<Page<RessourceResponse>>builder()
                .data(ressourceResponsePage)
                .message("Ressource list retrieved successfully!")
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
                .message("Ressource counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<RessourceResponse> updateOne(@RequestBody RessourceRequest dto){
        Instant startProcessing = Instant.now();
        RessourceResponse ressourceResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<RessourceResponse>builder()
                .data(ressourceResponse)
                .message("Ressource updated successfully!")
                .build();
    }


    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Void> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<Void>builder()
                .message("Ressource deleted successfully!")
                .build();
    }
}
