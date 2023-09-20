package bj.gouv.sineb.authservice.application.code.entity;

import bj.gouv.sineb.authservice.application.code.dto.request.PolicieUpdateRequest;
import bj.gouv.sineb.common.advice.exception.CustomException;
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
@Table(name = "auth_policie")
@EntityListeners(AuditingEntityListener.class)
public class Policie implements Serializable {

    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(updatable = false)
    private UUID trackingId;
    private boolean deleted;
    @JsonIgnore
    private boolean expired;
    private boolean takeEffectNow;
    private Instant startDate;
    private Instant endDate;
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
    @JoinColumn(name="ressource_id", nullable=false)
    private Ressource ressource;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="scope_id", nullable=false)
    private Scope scope;

    /*@OneToMany(mappedBy = "policie")
    private List<PolicieScope> policieScopeList = new CopyOnWriteArrayList<>();*/

    public void delete() {
        this.deleted = true;
    }

    public void update(PolicieUpdateRequest request) {
        if (this.takeEffectNow){
            throw new CustomException("Policie already active cannot be changed! You can delete it if necessary.");
        } else if (request.getStartDate().isBefore(Instant.now())) {
            throw new CustomException("Start date should not be in the past!");
        } else if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new CustomException("Start date should be before endDate!");
        }else {
            this.startDate = request.getStartDate();
            this.endDate = request.getEndDate();
        }
    }
}
