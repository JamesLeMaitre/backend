package bj.gouv.sineb.choreography.processor;

import bj.gouv.sineb.choreography.events.DomainEvent;
import reactor.core.publisher.Mono;

public interface EventProcessor<T extends DomainEvent, R extends DomainEvent>{

    Mono<R> process(T event);
}
