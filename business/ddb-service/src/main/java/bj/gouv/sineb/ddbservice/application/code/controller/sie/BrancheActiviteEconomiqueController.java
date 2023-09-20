package bj.gouv.sineb.ddbservice.application.code.controller.sie;


import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.sie.BrancheActiviteEconomiqueResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.sie.BrancheActiviteEconomiqueRequest;
import bj.gouv.sineb.ddbservice.application.code.service.sie.BrancheActiviteEconomiqueService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/sie/branche-activite-economique")
public class BrancheActiviteEconomiqueController {

    private final ApplicationCommonTask applicationCommonTask;
    private final BrancheActiviteEconomiqueService service;


    public BrancheActiviteEconomiqueController(ApplicationCommonTask applicationCommonTask, BrancheActiviteEconomiqueService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<BrancheActiviteEconomiqueResponse> save(@RequestBody BrancheActiviteEconomiqueRequest dto) {
        Instant startProcessing = Instant.now();
        BrancheActiviteEconomiqueResponse civiliteResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<BrancheActiviteEconomiqueResponse>builder()
                .data(civiliteResponse)
                .message("BrancheActiviteEconomique created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<BrancheActiviteEconomiqueResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        BrancheActiviteEconomiqueResponse civiliteResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<BrancheActiviteEconomiqueResponse>builder()
                .data(civiliteResponse)
                .message("BrancheActiviteEconomique retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<BrancheActiviteEconomiqueResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<BrancheActiviteEconomiqueResponse> civiliteResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<BrancheActiviteEconomiqueResponse>>builder()
                .data(civiliteResponsePage)
                .message("BrancheActiviteEconomique list retrieved successfully!")
                .build();
    }

    @GetMapping("/by-bac/{bacTrackingId}")
    public BaseResponse<Page<BrancheActiviteEconomiqueResponse>> getAllByBAEconomique(
            @PathVariable UUID bacTrackingId,
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<BrancheActiviteEconomiqueResponse> civiliteResponsePage = service.getAllByBlocActiviteEconomiqueId(bacTrackingId, page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<BrancheActiviteEconomiqueResponse>>builder()
                .data(civiliteResponsePage)
                .message("BrancheActiviteEconomique list by bloc activite economique retrieved successfully!")
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
                .message("BrancheActiviteEconomique counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<BrancheActiviteEconomiqueResponse> updateOne(@RequestBody BrancheActiviteEconomiqueRequest dto){
        Instant startProcessing = Instant.now();
        BrancheActiviteEconomiqueResponse civiliteResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<BrancheActiviteEconomiqueResponse>builder()
                .data(civiliteResponse)
                .message("BrancheActiviteEconomique updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<BrancheActiviteEconomiqueResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<BrancheActiviteEconomiqueResponse>builder()
                .message("BrancheActiviteEconomique deleted successfully!")
                .build();
    }
    
}
