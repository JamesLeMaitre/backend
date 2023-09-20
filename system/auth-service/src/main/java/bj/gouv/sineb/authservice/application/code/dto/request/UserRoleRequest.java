package bj.gouv.sineb.authservice.application.code.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRoleRequest {
    private UUID trackingId;
    @NotNull(message = "User id is required.")
    private UUID userTrackingId;
    @NotNull(message = "Role id is required.")
    private UUID roleTrackingId;
}
