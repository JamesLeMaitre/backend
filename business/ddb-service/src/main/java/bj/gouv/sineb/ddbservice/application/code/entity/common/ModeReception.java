package bj.gouv.sineb.ddbservice.application.code.entity.common;


import bj.gouv.sineb.common.domain.CommonEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Audited
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ddb_common_mode_reception")
@EntityListeners(AuditingEntityListener.class)
public class ModeReception extends CommonEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID trackingId;
    private String name;
    private String description;
}
