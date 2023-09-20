package bj.gouv.sineb.demandeservice.application.task;


import bj.gouv.sineb.demandeservice.application.event.AuditEventPublisher;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ApplicationCommonTask {

    private final AuditEventPublisher auditEventPublisher;

    public ApplicationCommonTask(AuditEventPublisher auditEventPublisher) {
        this.auditEventPublisher = auditEventPublisher;
    }

    public void logThisEvent(String body, Instant startProcessing) {
        Instant endProcessing = Instant.now();
        auditEventPublisher.publish(
                body,  startProcessing, endProcessing, 200
        );
    }
}
