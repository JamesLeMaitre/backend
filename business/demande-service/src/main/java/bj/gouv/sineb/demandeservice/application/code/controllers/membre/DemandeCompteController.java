package bj.gouv.sineb.demandeservice.application.code.controllers.membre;

import bj.gouv.sineb.common.dto.response.BaseResponse;
import bj.gouv.sineb.demandeservice.application.code.service.membre.DemandeCompteService;
import bj.gouv.sineb.demandeservice.application.task.ApplicationCommonTask;
import bj.gouv.sineb.demandeservice.common.dto.request.membre.DemandeCompteRequest;
import bj.gouv.sineb.demandeservice.common.dto.response.membre.DemandeCompteResponse;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/demande/demande_compte/")
@RequiredArgsConstructor
public class DemandeCompteController {

    private final ApplicationCommonTask applicationCommonTask;

    private final DemandeCompteService service;


    @PostMapping("save")
    public BaseResponse<DemandeCompteResponse> save(@RequestBody DemandeCompteRequest request) {

        boolean checkEmailValid = service.emailValid(request.getMail());
        System.out.println("************************");
        System.out.println(checkEmailValid);
        System.out.println("************************");
        boolean checkEmailExiting = service.emailPresent(request.getMail());

        if (!checkEmailValid) {
            return BaseResponse.<DemandeCompteResponse>builder()
                    .status(400)
                    .message("Email is not valid!")
                    .build();
        } else if (checkEmailExiting) {
            return BaseResponse.<DemandeCompteResponse>builder()
                    .status(409)
                    .message("Email is already exist!")
                    .build();
        }

        Instant startProcessing = Instant.now();
        DemandeCompteResponse demandeCompteResponse = service.save(request);
        applicationCommonTask.logThisEvent(request.toString(), startProcessing);
        return BaseResponse.<DemandeCompteResponse>builder()
                .data(demandeCompteResponse)
                .status(200)
                .message("DemandeCompte created successfully!")
                .build();
    }

    @PutMapping("update/{trackingId}")
    public BaseResponse<DemandeCompteResponse> update(@RequestBody DemandeCompteRequest request, @PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        DemandeCompteResponse demandeCompteResponse = service.update(request, trackingId);
        applicationCommonTask.logThisEvent(request.toString(), startProcessing);
        return BaseResponse.<DemandeCompteResponse>builder()
                .data(demandeCompteResponse)
                .status(200)
                .message("DemandeCompte updated successfully!")
                .build();
    }

    @DeleteMapping("delete/{trackingId}")
    public BaseResponse<DemandeCompteResponse> delete(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        DemandeCompteResponse demandeCompteResponse = service.delete(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<DemandeCompteResponse>builder()
                .data(demandeCompteResponse)
                .status(200)
                .message("DemandeCompte deleted successfully!")
                .build();
    }

    @GetMapping("{trackingId}")
    public BaseResponse<DemandeCompteResponse> getOne(@PathVariable UUID trackingId) {
        Instant startProcessing = Instant.now();
        DemandeCompteResponse demandeCompteResponse = service.getOne(trackingId);
        applicationCommonTask.logThisEvent(null, startProcessing);
        return BaseResponse.<DemandeCompteResponse>builder()
                .data(demandeCompteResponse)
                .status(200)
                .message("DemandeCompte deleted successfully!")
                .build();
    }

    @GetMapping("all")
    public BaseResponse<Page<DemandeCompteResponse>> getAll(
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<DemandeCompteResponse> demandeCompteResponsePage = service.getAll(page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<DemandeCompteResponse>>builder()
                .data(demandeCompteResponsePage)
                .status(200)
                .message("DemandeCompte list retrieved successfully!")
                .build();
    }

    @GetMapping("list-by-type-demande/{typeDemandeId}")
    public BaseResponse<Page<DemandeCompteResponse>> getAllByTypeDemande(
            @PathVariable UUID typeDemandeId,
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<DemandeCompteResponse> demandeCompteResponsePage = service.getAllByTypeDemande(typeDemandeId, page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<DemandeCompteResponse>>builder()
                .data(demandeCompteResponsePage)
                .status(200)
                .message("DemandeCompte list retrieved successfully!")
                .build();
    }

    @GetMapping("list-by-type-compte/{typeCompteId}")
    public BaseResponse<Page<DemandeCompteResponse>> getAllByTypeCompte(
            @PathVariable UUID typeCompteId,
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<DemandeCompteResponse> demandeCompteResponsePage = service.getAllByTypeCompte(typeCompteId, page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<DemandeCompteResponse>>builder()
                .data(demandeCompteResponsePage)
                .status(200)
                .message("DemandeCompte list retrieved successfully!")
                .build();
    }


    @GetMapping("list-by-civilite/{civiliteId}")
    public BaseResponse<Page<DemandeCompteResponse>> getAllByCivilite(
            @PathVariable UUID civiliteId,
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<DemandeCompteResponse> demandeCompteResponsePage = service.getAllByCivilite(civiliteId, page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<DemandeCompteResponse>>builder()
                .data(demandeCompteResponsePage)
                .status(200)
                .message("DemandeCompte list retrieved successfully!")
                .build();
    }

    @GetMapping("list-by-pays/{paysId}")
    public BaseResponse<Page<DemandeCompteResponse>> getAllByPays(
            @PathVariable UUID paysId,
            @RequestParam(defaultValue = "${pagination.default-page}") @Min(value = 0, message = "page should be greater than or equal to 0") int page,
            @RequestParam(defaultValue = "${pagination.default-size}") @Min(value = 1, message = "size should be greater than or equal to 1") int size){
        Instant startProcessing = Instant.now();
        Page<DemandeCompteResponse> demandeCompteResponsePage = service.getAllByPays(paysId, page, size);
        applicationCommonTask.logThisEvent(null, startProcessing);

        return BaseResponse.<Page<DemandeCompteResponse>>builder()
                .data(demandeCompteResponsePage)
                .status(200)
                .message("DemandeCompte list retrieved successfully!")
                .build();
    }

}
