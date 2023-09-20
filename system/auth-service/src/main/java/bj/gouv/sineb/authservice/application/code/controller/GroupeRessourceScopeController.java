package bj.gouv.sineb.authservice.application.code.controller;

import bj.gouv.sineb.authservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.authservice.application.code.dto.request.GroupeRessourceScopeRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.BaseResponse;
import bj.gouv.sineb.authservice.application.code.dto.response.GroupeRessourceScopeResponse;
import bj.gouv.sineb.authservice.application.code.service.GroupeRessourceScopeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/auth/groupes-ressources-scopes")
public class GroupeRessourceScopeController {

    private final ApplicationCommonTask applicationCommonTask;
    private final GroupeRessourceScopeService service;
    

    public GroupeRessourceScopeController(ApplicationCommonTask applicationCommonTask,
                                          GroupeRessourceScopeService service) {
        this.applicationCommonTask = applicationCommonTask;
        this.service = service;
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Void> save(@RequestBody GroupeRessourceScopeRequest dto){
        Instant startProcessing = Instant.now();
        service.save(dto);
        applicationCommonTask.logThisEvent(dto.toString(), startProcessing);
        return BaseResponse.<Void>builder()
                .message("GroupeRessourceScope created successfully!")
                .build();
    }


    /*@GetMapping("/{trackingId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<GroupeRessourceScope> getOne(@PathVariable UUID trackingId){
        return BaseResponse.<GroupeRessourceScope>builder()
                .data(service.getOne(trackingId))
                .event("GroupeRessourceScope retrieved successfully!")
                .build();
    }*/

    /*@GetMapping
    public BaseResponse<List<GroupeRessourceScope>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, event = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, event = "size should be greater than or equal to 1") int size,
            @RequestParam boolean deleted
    ){
        return BaseResponse.<List<GroupeRessourceScope>>builder()
                .data(service.getAll(page, size, deleted))
                .event("GroupeRessourceScope list retrieved successfully!")
                .build();
    }*/

    
    @GetMapping("/count")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Long> count(){
        Instant startProcessing = Instant.now();
        Long count = service.count();
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<Long>builder()
                .data(count)
                .message("GroupeRessourceScope counted successfully!")
                .build();
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Void> addSpecificScope(@RequestParam UUID groupeTrackingId, @RequestParam UUID ressourceTrackingId, @RequestParam UUID newScopeTrackingId){
        Instant startProcessing = Instant.now();
        service.addSpecificScope(groupeTrackingId, ressourceTrackingId, newScopeTrackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<Void>builder()
                .message("Scope added for ressource assigned to the groupe successfully!")
                .build();
    }

    @DeleteMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<GroupeRessourceScopeResponse> deleteOne(@RequestParam UUID groupeTrackingId, @RequestParam UUID ressourceTrackingId, @RequestParam UUID scopeTrackingId){
        Instant startProcessing = Instant.now();
        service.deleteOne(groupeTrackingId, ressourceTrackingId, scopeTrackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<GroupeRessourceScopeResponse>builder()
                .message("GroupeRessourceScope deleted successfully!")
                .build();
    }
}
