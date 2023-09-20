package bj.gouv.sineb.ddbservice.application.code.entity.rc_rbdd;


import bj.gouv.sineb.common.domain.CommonEntity;
import bj.gouv.sineb.ddbservice.application.code.dto.request.rc_rbdd.TypeRessourceDocumentaireRequest;
import jakarta.persistence.*;
import lombok.*;
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
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ddb_rbdd_type_ressource_documentaire")
@EntityListeners(AuditingEntityListener.class)
public class TypeRessourceDocumentaire extends CommonEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID trackingId;
    private String name;
    private String description;
}
