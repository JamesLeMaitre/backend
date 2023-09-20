package bj.gouv.sineb.demandeservice.application.code.entity.membre;

import bj.gouv.sineb.common.domain.CommonEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;


@Entity
@Table(name = "membre_type_compte")
@Audited
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypeCompte extends CommonEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID trackingId;
    private String name;
    private String description;

}
