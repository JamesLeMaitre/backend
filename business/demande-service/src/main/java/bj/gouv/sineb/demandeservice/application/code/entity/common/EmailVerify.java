package bj.gouv.sineb.demandeservice.application.code.entity.common;

import bj.gouv.sineb.common.domain.CommonEntity;
import bj.gouv.sineb.demandeservice.application.code.entity.membre.DemandeCompte;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Audited
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "common_email_verify")
@EntityListeners(AuditingEntityListener.class)
public class EmailVerify extends CommonEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID trackingId;
    private int code;
    private String email;
    @Column(nullable = false)
    private boolean status;
    private Date expiration;

}
