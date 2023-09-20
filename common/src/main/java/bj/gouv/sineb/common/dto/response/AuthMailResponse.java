package bj.gouv.sineb.common.dto.response;


import bj.gouv.sineb.common.enums.EmailStatus;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthMailResponse {
    private String id;
    private String sagaId;
    private String toUserId;
    private EmailStatus emailStatus;
    String message;
}
