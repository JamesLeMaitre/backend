package bj.gouv.sineb.demandeservice.common.dto.request.common;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CiviliteRequest implements Serializable {
    @NotNull(message = "Civilite name is required.")
    @NotEmpty(message = "Civilite name should not be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Civilite name should not contain special characters and space except _.")
    private String name;
    @NotNull(message = "Civilite description is required.")
    @NotEmpty(message = "Civilite description should not be empty.")
    private String description;
}
