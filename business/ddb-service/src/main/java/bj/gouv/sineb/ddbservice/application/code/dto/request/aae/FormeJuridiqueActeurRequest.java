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
public class FormeJuridiqueActeurRequest {
    private UUID trackingId;
    @NotNull(message = "FormeJuridiqueActeur name is required.")
    @NotEmpty(message = "FormeJuridiqueActeur name should not be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "FormeJuridiqueActeur name should not contain special characters and space except _.")
    private String name;
    @NotNull(message = "FormeJuridiqueActeur description is required.")
    @NotEmpty(message = "FormeJuridiqueActeur description should not be empty.")
    private String description;
}
