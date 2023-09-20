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
public class TitreActeurRequest {
    private UUID trackingId;
    @NotNull(message = "TitreActeur name is required.")
    @NotEmpty(message = "TitreActeur name should not be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "TitreActeur name should not contain special characters and space except _.")
    private String name;
    @NotNull(message = "TitreActeur description is required.")
    @NotEmpty(message = "TitreActeur description should not be empty.")
    private String description;
}
