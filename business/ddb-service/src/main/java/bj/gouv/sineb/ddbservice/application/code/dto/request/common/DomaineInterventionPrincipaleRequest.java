package bj.gouv.sineb.ddbservice.application.code.dto.request.common;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DomaineInterventionPrincipaleRequest {
    private UUID trackingId;
    @NotNull(message = "FormeEnergiePrimaire name is required.")
    @NotEmpty(message = "FormeEnergiePrimaire name should not be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "FormeEnergiePrimaire name should not contain special characters and space except _.")
    private String name;
    @NotNull(message = "FormeEnergiePrimaire description is required.")
    @NotEmpty(message = "FormeEnergiePrimaire description should not be empty.")
    private String description;
}
