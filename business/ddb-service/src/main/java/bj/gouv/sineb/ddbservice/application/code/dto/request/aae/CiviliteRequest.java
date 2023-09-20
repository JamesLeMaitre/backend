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
public class CiviliteRequest {
    private UUID trackingId;
    @NotNull(message = "Civilite name is required.")
    @NotEmpty(message = "Civilite name should not be empty.")
//    @Pattern(regexp = "^[\\p{L} '-]+$", message = "Civilite name should not contain special characters and space except _.")
    private String name;
    @NotNull(message = "Civilite description is required.")
    @NotEmpty(message = "Civilite description should not be empty.")
    private String description;
}
