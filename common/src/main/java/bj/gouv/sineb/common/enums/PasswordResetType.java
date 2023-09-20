package bj.gouv.sineb.common.enums;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordResetType {

    public static final String AUTH = "AUTH";
    public static final String NO_AUTH = "NO_AUTH";
}
