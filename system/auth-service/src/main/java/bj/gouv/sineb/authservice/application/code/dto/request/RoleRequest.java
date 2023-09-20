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
public class RoleRequest {
    private UUID trackingId;
    @NotNull(message = "Role name is required.")
    @NotEmpty(message = "Role name should not be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Role name should not contain special characters and space except _.")
    private String name;
    @NotNull(message = "Role description is required.")
    @NotEmpty(message = "Role description should not be empty.")
    private String description;
}
