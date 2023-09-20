package bj.gouv.sineb.authservice.application.code.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
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
@Table(name = "auth_journal_event")
@EntityListeners(AuditingEntityListener.class)
public class JournalEvent implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(updatable = false)
    private UUID trackingId;
    private String userAgent;
    private String contentType;
    private String contentLength;
    private String method;
    private String requestUrl;
    private String requestUri;
    @Column(length = 2000)
    private String queryString;
    private String protocol;
    private String remoteAddr;
    private int remotePort;
    private String localAddr;
    private int localPort;
    private String scheme;
    private long timestamp;
    private long processingTimeMillis;
    private int statusCode;
    @Column(length = 2000)
    private String body;
    @Column(length = 2000)
    private String userOfActionTrackingId;
    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
    private String createdBy;
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

}
