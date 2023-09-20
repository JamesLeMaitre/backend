package bj.gouv.sineb.ddbservice.application.code.dto.request.aae;

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
public class RoleActeurRequest {
    private UUID trackingId;
    @NotNull(message = "RoleActeur name is required.")
    @NotEmpty(message = "RoleActeur name should not be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "RoleActeur name should not contain special characters and space except _.")
    private String name;
    @NotNull(message = "RoleActeur description is required.")
    @NotEmpty(message = "RoleActeur description should not be empty.")
    private String description;
}
