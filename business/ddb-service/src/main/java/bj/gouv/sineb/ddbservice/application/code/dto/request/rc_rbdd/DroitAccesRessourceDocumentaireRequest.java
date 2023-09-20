package bj.gouv.sineb.ddbservice.application.code.dto.request.rc_rbdd;

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
public class DroitAccesRessourceDocumentaireRequest {
    private UUID trackingId;
    @NotNull(message = "DroitAccesRessourceDocumentaire name is required.")
    @NotEmpty(message = "DroitAccesRessourceDocumentaire name should not be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "DroitAccesRessourceDocumentaire name should not contain special characters and space except _.")
    private String name;
    @NotNull(message = "DroitAccesRessourceDocumentaire description is required.")
    @NotEmpty(message = "DroitAccesRessourceDocumentaire description should not be empty.")
    private String description;
}
