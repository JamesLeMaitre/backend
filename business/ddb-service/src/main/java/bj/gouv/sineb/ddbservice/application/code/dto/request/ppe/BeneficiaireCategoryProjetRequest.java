package bj.gouv.sineb.ddbservice.application.code.dto.request.ppe;

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
public class BeneficiaireCategoryProjetRequest {
    private UUID trackingId;
    @NotNull(message = "BeneficiaireProjet name is required.")
    @NotEmpty(message = "BeneficiaireProjet name should not be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "BeneficiaireProjet name should not contain special characters and space except _.")
    private String name;
    @NotNull(message = "BeneficiaireProjet description is required.")
    @NotEmpty(message = "BeneficiaireProjet description should not be empty.")
    private String description;
}
