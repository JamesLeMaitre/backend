package bj.gouv.sineb.choreography.publisher;


import bj.gouv.sineb.choreography.events.DomainEvent;
import reactor.core.publisher.Flux;

public interface EventPublisher<T extends DomainEvent> {

    Flux<T> publish();
}
