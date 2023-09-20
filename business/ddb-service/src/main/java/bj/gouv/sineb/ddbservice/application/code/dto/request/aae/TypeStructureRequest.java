package bj.gouv.sineb.ddbservice.application.code.dto.request.aae;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;


@Data
@Builder
public class TypeStructureRequest implements Serializable {
    @NotNull(message = "Type Structure name is required.")
    @NotEmpty(message = "Type Structure name should not be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Type Structure name should not contain special characters and space except _.")
    private String name;
    @NotNull(message = "Type Structure description is required.")
    @NotEmpty(message = "Type Structure description should not be empty.")
    private String description;
}