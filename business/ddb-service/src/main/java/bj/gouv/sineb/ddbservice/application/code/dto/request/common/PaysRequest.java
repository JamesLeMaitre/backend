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
public class PaysRequest {
    private String code;
    private String shortName;
    @NotNull(message = "Pays name is required.")
    @NotEmpty(message = "Pays name should not be empty.")
    @Pattern(regexp = "^[\\p{L} '-]+$", message = "Pays name should not contain special characters and space except _.")
    private String name;
    @NotNull(message = "Pays description is required.")
    @NotEmpty(message = "Pays description should not be empty.")
    private String description;
}
