package bj.gouv.sineb.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MailTypeAndSubject {
    private String type;
    private String libelle;
}
