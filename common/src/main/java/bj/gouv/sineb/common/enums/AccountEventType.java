package bj.gouv.sineb.common.enums;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountEventType {

    public static final String REGISTRATION = "registration";
    public static final String EMAIL_RESET = "email-reset";
}
