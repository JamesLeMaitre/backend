package bj.gouv.sineb.ddbservice.application.code.dto.request.sie;

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
public class BlocActiviteEconomiqueRequest {
    private UUID trackingId;
    @NotNull(message = "BlocActiviteEconomique name is required.")
    @NotEmpty(message = "BlocActiviteEconomique name should not be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "BlocActiviteEconomique name should not contain special characters and space except _.")
    private String name;
    @NotNull(message = "BlocActiviteEconomique description is required.")
    @NotEmpty(message = "BlocActiviteEconomique description should not be empty.")
    private String description;
}
