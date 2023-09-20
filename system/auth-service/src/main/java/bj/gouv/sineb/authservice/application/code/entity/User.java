package bj.gouv.sineb.authservice.application.code.entity;

import bj.gouv.sineb.authservice.application.code.dto.request.UserRequest;
import bj.gouv.sineb.common.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Audited
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "auth_user")
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(updatable = false)
    private UUID trackingId;
    @Column(unique = true)
    private String email;
    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String phone;
    @JsonIgnore
    private String password;
    private String tempPwd;
    private Instant tempPwdExpiredAt;
    @JsonIgnore
    private boolean enabled;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    @JsonIgnore
    private boolean deleted;
    private boolean passwordChangedOnce;
    private boolean accountNonExpired; // Utiliser ceci pour controllers les compte dont les informations ne sont pas mise a jour apres le delay
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private Instant accessTokenExpiredAt;
    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
    private String createdBy;
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @LastModifiedBy
    @Column(name = "last_modified_by", length = 50)
    private String lastModifiedBy;
    @LastModifiedDate
    @Column(name = "last_modified_at")
    private Instant lastModifiedAt;

    // THIS CREATE STACKOVERFLOW EXCEPTION WHILE SAVE OR READING ENTITY DATA

    /*@OneToMany(mappedBy = "email")
    private List<VerificationToken> verificationTokenList = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "email")
    private List<Policie> policieList = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "email")
    private List<GroupeUser> groupeUserList = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "email")
    private List<UserRole> userRoleList = new CopyOnWriteArrayList<>();*/


    public void update(UserRequest request) {
        this.firstname = request.getFirstname();
        this.lastname = request.getLastname();
    }

    public void delete() {
        this.deleted = true;
    }

    public void disable() {
        this.enabled = false;
    }
}
