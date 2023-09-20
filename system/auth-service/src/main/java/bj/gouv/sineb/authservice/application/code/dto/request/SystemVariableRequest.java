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
public class SystemVariableRequest {
    private UUID trackingId;
    @NotNull(message = "System variable key is required.")
    @NotEmpty(message = "System variable key should not be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "System variable key should not contain special characters and space except _.")
    private String key;
    @NotNull(message = "System variable value is required.")
    private String value;
    @NotNull(message = "System variable description is required.")
    @NotEmpty(message = "System variable description should not be empty.")
    private String description;
}
