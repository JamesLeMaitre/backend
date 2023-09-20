package bj.gouv.sineb.ddbservice.application.code.controller.ppe;


import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.ppe.BeneficiaireCategoryProjetResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.ppe.BeneficiaireCategoryProjetRequest;
import bj.gouv.sineb.ddbservice.application.code.service.ppe.BeneficiaireCategoryProjetService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/ppe/beneficiaire-category-projet")
public class BeneficiaireCategoryProjetController {

    private final ApplicationCommonTask applicationCommonTask;
    private final BeneficiaireCategoryProjetService service;


    public BeneficiaireCategoryProjetController(ApplicationCommonTask applicationCommonTask, BeneficiaireCategoryProjetService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<BeneficiaireCategoryProjetResponse> save(@RequestBody BeneficiaireCategoryProjetRequest dto) {
        Instant startProcessing = Instant.now();
        BeneficiaireCategoryProjetResponse beneficiaireCategoryProjetResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<BeneficiaireCategoryProjetResponse>builder()
                .data(beneficiaireCategoryProjetResponse)
                .message("BeneficiaireCategoryProjet created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<BeneficiaireCategoryProjetResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        BeneficiaireCategoryProjetResponse beneficiaireCategoryProjetResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<BeneficiaireCategoryProjetResponse>builder()
                .data(beneficiaireCategoryProjetResponse)
                .message("BeneficiaireCategoryProjet retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<BeneficiaireCategoryProjetResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<BeneficiaireCategoryProjetResponse> beneficiaireCategoryProjetResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<BeneficiaireCategoryProjetResponse>>builder()
                .data(beneficiaireCategoryProjetResponsePage)
                .message("BeneficiaireCategoryProjet list retrieved successfully!")
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
                .message("BeneficiaireCategoryProjet counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<BeneficiaireCategoryProjetResponse> updateOne(@RequestBody BeneficiaireCategoryProjetRequest dto){
        Instant startProcessing = Instant.now();
        BeneficiaireCategoryProjetResponse beneficiaireCategoryProjetResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<BeneficiaireCategoryProjetResponse>builder()
                .data(beneficiaireCategoryProjetResponse)
                .message("BeneficiaireCategoryProjet updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<BeneficiaireCategoryProjetResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<BeneficiaireCategoryProjetResponse>builder()
                .message("BeneficiaireCategoryProjet deleted successfully!")
                .build();
    }
}
