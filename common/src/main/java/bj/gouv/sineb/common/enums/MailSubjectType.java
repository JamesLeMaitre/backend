package bj.gouv.sineb.common.enums;

import bj.gouv.sineb.common.dto.MailTypeAndSubject;
import lombok.Data;

import java.util.List;

@Data
public class MailSubjectType {

    public static final List<MailTypeAndSubject> values = List.of(
            new MailTypeAndSubject(
                    "ACCOUNT_VALIDATION",
                    "CONFIRMATION D'ADRESSE EMAIL ET VALIDATION DE COMPTE"
            ),
            new MailTypeAndSubject(
                    "PASSWORD_RESET",
                    "REINITIALISATION DE MOT DE PASSE"
            ),
            new MailTypeAndSubject(
                    "EMAIL_RESET",
                            "CHANGEMENT D'ADRESSE EMAIL"
    )
    );
}
