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
public class DepartementRequest {
    private UUID trackingId;
    @NotNull(message = "Departement name is required.")
    @NotEmpty(message = "Departement name should not be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Departement name should not contain special characters and space except _.")
    private String name;
    @NotNull(message = "Departement description is required.")
    @NotEmpty(message = "Departement description should not be empty.")
    private String description;
}
