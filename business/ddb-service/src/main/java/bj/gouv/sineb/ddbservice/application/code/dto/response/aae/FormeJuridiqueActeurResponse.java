package bj.gouv.sineb.ddbservice.application.code.dto.response.aae;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FormeJuridiqueActeurResponse implements Serializable {
    private UUID trackingId;
    private String name;
    private String codeAuto;
    private String description;
    private String createdBy;
    private Instant createdAt;
    private String lastModifiedBy;
    private Instant lastModifiedAt;
}
