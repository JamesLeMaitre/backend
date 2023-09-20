package bj.gouv.sineb.authservice.application.code.dto.request;

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
public class AppEventRequest {
    private UUID trackingId;
    @NotNull(message = "AppEvent name is required.")
    @NotEmpty(message = "AppEvent name should not be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "AppEvecnt name should not contain special characters and space except _.")
    private String name;
    @NotNull(message = "AppEvent description is required.")
    @NotEmpty(message = "AppEvent description should not be empty.")
    private String description;
}
