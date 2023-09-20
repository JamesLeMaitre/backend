package bj.gouv.sineb.ddbservice.application.code.dto.request.common;

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
public class ComiteRequest {
    private UUID trackingId;
    @NotNull(message = "Comite name is required.")
    @NotEmpty(message = "Comite name should not be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Comite name should not contain special characters and space except _.")
    private String name;
    @NotNull(message = "Comite description is required.")
    @NotEmpty(message = "Comite description should not be empty.")
    private String description;
}
