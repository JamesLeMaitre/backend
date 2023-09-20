package bj.gouv.sineb.authservice.application.code.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserResponse {
    private UUID trackingId;
    private String email;
    private String firstname;
    private String lastname;
    private String phone;
    private Instant createdAt;
    private String createdBy;
    private Instant lastModifiedAt;
    private String lastModifiedBy;;
    private Instant accessTokenExpiredAt;
    RoleResponse roleResponse;
    List<String> scopes = new ArrayList<>();
}
