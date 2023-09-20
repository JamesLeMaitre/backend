package bj.gouv.sineb.authservice.application.code.dto.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthEmailRequestEventPayload {
    private String token;
    private String eventType;
    private String otherEmail;
    private String temp;
    private UUID toUserId;
    public String toUserEmail;
    public String toUserFirstname;
    public String toUserLastName;
    @JsonProperty
    private String emailUserStatus;
    private Instant createdAt;

}
