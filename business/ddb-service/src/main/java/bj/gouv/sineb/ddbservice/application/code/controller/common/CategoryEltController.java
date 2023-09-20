package bj.gouv.sineb.ddbservice.application.code.controller.common;


import bj.gouv.sineb.ddbservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.BaseResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.CategoryEltResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.CategoryEltRequest;
import bj.gouv.sineb.ddbservice.application.code.service.common.CategoryEltService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/ddb/common/category-elt")
public class CategoryEltController {

    private final ApplicationCommonTask applicationCommonTask;
    private final CategoryEltService service;


    public CategoryEltController(ApplicationCommonTask applicationCommonTask, CategoryEltService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    public BaseResponse<CategoryEltResponse> save(@RequestBody CategoryEltRequest dto) {
        Instant startProcessing = Instant.now();
        CategoryEltResponse categoryEltResponse = service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<CategoryEltResponse>builder()
                .data(categoryEltResponse)
                .message("CategoryElt created successfully!")
                .build();
    }



    @GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<CategoryEltResponse> getOneByTrackingId(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        CategoryEltResponse categoryEltResponse = service.getOneByTrackingId(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<CategoryEltResponse>builder()
                .data(categoryEltResponse)
                .message("CategoryElt retrieved successfully!")
                .build();
    }

    @GetMapping
    public BaseResponse<Page<CategoryEltResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<CategoryEltResponse> categoryEltResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<CategoryEltResponse>>builder()
                .data(categoryEltResponsePage)
                .message("CategoryElt list retrieved successfully!")
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
                .message("CategoryElt counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<CategoryEltResponse> updateOne(@RequestBody CategoryEltRequest dto){
        Instant startProcessing = Instant.now();
        CategoryEltResponse categoryEltResponse = service.updateOne(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<CategoryEltResponse>builder()
                .data(categoryEltResponse)
                .message("CategoryElt updated successfully!")
                .build();
    }

    @DeleteMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<CategoryEltResponse> deleteOne(@PathVariable UUID trackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<CategoryEltResponse>builder()
                .message("CategoryElt deleted successfully!")
                .build();
    }
}
