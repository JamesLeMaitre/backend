package bj.gouv.sineb.ddbservice.application.code.controller.aae;

import bj.gouv.sineb.common.dto.response.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.TypeStructureRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.TypeStructureResponse;
import bj.gouv.sineb.ddbservice.application.code.service.aae.TypeStructureService;
import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/demande/type_structure/")
@RequiredArgsConstructor
public class TypeStructureController {

    private final ApplicationCommonTask applicationCommonTask;

    private final TypeStructureService service;

    @PostMapping("save")
    public BaseResponse<TypeStructureResponse> save(@RequestBody TypeStructureRequest request) {
        Instant startProcessing = Instant.now();
        TypeStructureResponse typeStructureResponse = service.save(request);
        applicationCommonTask.logThisEvent(request.toString(), startProcessing);
        return BaseResponse.<TypeStructureResponse>builder()
                .data(typeStructureResponse)
                .status(200)
                .message("TypeStructure created successfully!")
                .build();
    }

    @PutMapping("update/{trackingId}")
    public BaseResponse<TypeStructureResponse> update(@RequestBody TypeStructureRequest request, @PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        TypeStructureResponse typeStructureResponse = service.update(request, trackingId);
        applicationCommonTask.logThisEvent(request.toString(), startProcessing);
        return BaseResponse.<TypeStructureResponse>builder()
                .data(typeStructureResponse)
                .status(200)
                .message("TypeStructure updated successfully!")
                .build();
    }

    @DeleteMapping("delete/{trackingId}")
    public BaseResponse<TypeStructureResponse> delete(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        TypeStructureResponse typeStructureResponse = service.delete(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<TypeStructureResponse>builder()
                .data(typeStructureResponse)
                .status(200)
                .message("TypeStructure deleted successfully!")
                .build();
    }

    @GetMapping("{trackingId}")
    public BaseResponse<TypeStructureResponse> getOne(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        TypeStructureResponse typeStructureResponse = service.getOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<TypeStructureResponse>builder()
                .data(typeStructureResponse)
                .status(200)
                .message("TypeStructure deleted successfully!")
                .build();
    }

    @GetMapping("all")
    public BaseResponse<Page<TypeStructureResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<TypeStructureResponse> typeStructureResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<TypeStructureResponse>>builder()
                .data(typeStructureResponsePage)
                .status(200)
                .message("TypeStructure list retrieved successfully!")
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
                .message("TypeStructure counted successfully!")
                .build();
    }


}
