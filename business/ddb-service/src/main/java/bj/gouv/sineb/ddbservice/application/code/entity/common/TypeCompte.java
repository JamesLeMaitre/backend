package bj.gouv.sineb.ddbservice.application.code.entity.common;

import bj.gouv.sineb.common.domain.CommonEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Audited
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "membre_type_compte")
public class TypeCompte extends CommonEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID trackingId;
    private String name;
    private String description;

}