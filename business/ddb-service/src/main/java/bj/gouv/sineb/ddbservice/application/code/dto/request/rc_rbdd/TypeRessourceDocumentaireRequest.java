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
public class TypeRessourceDocumentaireRequest {
    private UUID trackingId;
    @NotNull(message = "TypeRessourceDocumentaire name is required.")
    @NotEmpty(message = "TypeRessourceDocumentaire name should not be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "TypeRessourceDocumentaire name should not contain special characters and space except _.")
    private String name;
    @NotNull(message = "TypeRessourceDocumentaire description is required.")
    @NotEmpty(message = "TypeRessourceDocumentaire description should not be empty.")
    private String description;
}
