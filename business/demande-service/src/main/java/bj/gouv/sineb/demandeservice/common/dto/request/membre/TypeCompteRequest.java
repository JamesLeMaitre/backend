package bj.gouv.sineb.demandeservice.common.dto.request.membre;

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
public class TypeCompteRequest implements Serializable {
    @NotNull(message = "Type Compte name is required.")
    @NotEmpty(message = "Type Compte name should not be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Type Compte name should not contain special characters and space except _.")
    private String name;
    @NotNull(message = "Type Compte description is required.")
    @NotEmpty(message = "Type Compte description should not be empty.")
    private String description;

}
