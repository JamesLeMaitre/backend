package bj.gouv.sineb.authservice.application.code.entity;

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
@Table(name = "auth_groupe_user")
@EntityListeners(AuditingEntityListener.class)
public class GroupeUser implements Serializable {

    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(updatable = false)
    private UUID trackingId;
    @JsonIgnore
    private boolean deleted;
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
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="groupe_id", nullable=false)
    private Groupe groupe;

    public void delete() {
        this.deleted = true;
    }
}
