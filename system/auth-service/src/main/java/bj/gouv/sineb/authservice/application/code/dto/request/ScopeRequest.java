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
public class ScopeRequest {
    private UUID trackingId;
    @NotNull(message = "Scope name is required.")
    @NotEmpty(message = "Scope name should not be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Scope name should not contain special characters and space except _.")
    private String name;
    @NotNull(message = "Scope description is required.")
    @NotEmpty(message = "Scope description should not be empty.")
    private String description;
}
