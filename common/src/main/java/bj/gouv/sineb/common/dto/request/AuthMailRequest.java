package bj.gouv.sineb.common.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthMailRequest {
    private String token;
    private String eventType;
    private String otherEmail;
    private String temp;
    public String  userEmail;
    public String  userFirstName;
    public String  userLastName;
}
