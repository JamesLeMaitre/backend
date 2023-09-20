package bj.gouv.sineb.choreography.events;

import java.time.Instant;

public interface DomainEvent {
    Instant createdAt();
}
