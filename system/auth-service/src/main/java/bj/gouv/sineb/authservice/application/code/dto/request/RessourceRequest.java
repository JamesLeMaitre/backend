package bj.gouv.sineb.authservice.application.code.dto.request;

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
public class RessourceRequest {
    private UUID trackingId;
    @NotNull(message = "Ressource name is required.")
    @NotEmpty(message = "Ressource name should not be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Ressource name should not contain special characters and space except _.")
    private String name;
    @NotNull(message = "Ressource description is required.")
    @NotEmpty(message = "Ressource description should not be empty.")
    private String description;
}
