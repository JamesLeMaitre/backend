package bj.gouv.sineb.common.domain;

import bj.gouv.sineb.common.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private UUID id;
    private UUID trackingId;
    private String email;
    private String firstname;
    private String lastname;
    private String phone;
    private String password;
    private String tempPwd;
    private Instant tempPwdExpiredAt;
    private boolean enabled;
    private UserStatus userStatus;
    private boolean deleted;
    private boolean passwordChangedOnce;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private Instant accessTokenExpiredAt;
    private String createdBy;
    private Instant createdAt;
    private String lastModifiedBy;
    private Instant lastModifiedAt;
}
