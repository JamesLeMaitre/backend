package bj.gouv.sineb.demandeservice.application.code.entity.common;


import bj.gouv.sineb.common.domain.CommonEntity;
import bj.gouv.sineb.demandeservice.common.dto.request.common.PaysRequest;
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
@Table(name = "common_pays")
@EntityListeners(AuditingEntityListener.class)
public class Pays extends CommonEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID trackingId;
    private String code;
    private String name;
    private String shortName;
    private String description;

}
