package bj.gouv.sineb.authservice.application.code.dto.simple.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MailTypeAndSubject {
    private String type;
    private String libelle;
}
