package bj.gouv.sineb.choreography.processor;

import bj.gouv.sineb.choreography.events.DomainEvent;
import bj.gouv.sineb.choreography.events.email.RequestEmailResetEvent;
import bj.gouv.sineb.choreography.events.email.UserApprovalEvent;
import reactor.core.publisher.Mono;

public interface RequestEmailResetEventProcessor<R extends DomainEvent> extends EventProcessor<RequestEmailResetEvent, R> {

    @Override
    default Mono<R> process(RequestEmailResetEvent event) {
        return switch (event){
            case RequestEmailResetEvent.RequestEmailResetEventCreated e -> this.handle(e);
            case RequestEmailResetEvent.RequestEmailResetEventFailed e -> this.handle(e);
            case RequestEmailResetEvent.RequestEmailResetEventCompleted e -> this.handle(e);
        };
    }

    Mono<R> handle(RequestEmailResetEvent.RequestEmailResetEventCreated event);

    Mono<R> handle(RequestEmailResetEvent.RequestEmailResetEventFailed event);

    Mono<R> handle(RequestEmailResetEvent.RequestEmailResetEventCompleted event);

}
