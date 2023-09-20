package bj.gouv.sineb.ddbservice.application.code.controller.sie;

import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.sie.BlocActiviteEconomiqueResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.sie.BlocActiviteEconomiqueRequest;
import bj.gouv.sineb.ddbservice.application.code.service.sie.BlocActiviteEconomiqueService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/sie/bloc-activite-economique")
public class BlocActiviteEconomiqueController {

    private final ApplicationCommonTask applicationCommonTask;
    private final BlocActiviteEconomiqueService service;


    public BlocActiviteEconomiqueController(ApplicationCommonTask applicationCommonTask, BlocActiviteEconomiqueService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<BlocActiviteEconomiqueResponse> save(@RequestBody BlocActiviteEconomiqueRequest dto) {
        Instant startProcessing = Instant.now();
        BlocActiviteEconomiqueResponse blocActiviteEconomiqueResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<BlocActiviteEconomiqueResponse>builder()
                .data(blocActiviteEconomiqueResponse)
                .message("BlocActiviteEconomique created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<BlocActiviteEconomiqueResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        BlocActiviteEconomiqueResponse blocActiviteEconomiqueResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<BlocActiviteEconomiqueResponse>builder()
                .data(blocActiviteEconomiqueResponse)
                .message("BlocActiviteEconomique retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<BlocActiviteEconomiqueResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<BlocActiviteEconomiqueResponse> blocActiviteEconomiqueResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<BlocActiviteEconomiqueResponse>>builder()
                .data(blocActiviteEconomiqueResponsePage)
                .message("BlocActiviteEconomique list retrieved successfully!")
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
                .message("BlocActiviteEconomique counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<BlocActiviteEconomiqueResponse> updateOne(@RequestBody BlocActiviteEconomiqueRequest dto){
        Instant startProcessing = Instant.now();
        BlocActiviteEconomiqueResponse blocActiviteEconomiqueResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<BlocActiviteEconomiqueResponse>builder()
                .data(blocActiviteEconomiqueResponse)
                .message("BlocActiviteEconomique updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<BlocActiviteEconomiqueResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<BlocActiviteEconomiqueResponse>builder()
                .message("BlocActiviteEconomique deleted successfully!")
                .build();
    }
}
