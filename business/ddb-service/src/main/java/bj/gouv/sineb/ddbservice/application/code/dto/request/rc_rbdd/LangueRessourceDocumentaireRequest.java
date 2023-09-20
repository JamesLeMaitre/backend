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
public class LangueRessourceDocumentaireRequest {
    private UUID trackingId;
    @NotNull(message = "FormatRessourceDocumentaire name is required.")
    @NotEmpty(message = "FormatRessourceDocumentaire name should not be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "FormatRessourceDocumentaire name should not contain special characters and space except _.")
    private String name;
    @NotNull(message = "FormatRessourceDocumentaire description is required.")
    @NotEmpty(message = "FormatRessourceDocumentaire description should not be empty.")
    private String description;
}
