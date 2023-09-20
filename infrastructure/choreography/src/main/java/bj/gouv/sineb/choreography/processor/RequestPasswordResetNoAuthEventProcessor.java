package bj.gouv.sineb.choreography.processor;

import bj.gouv.sineb.choreography.events.DomainEvent;
import bj.gouv.sineb.choreography.events.email.RequestPasswordResetNoAuthEvent;
import bj.gouv.sineb.choreography.events.email.UserApprovalEvent;
import reactor.core.publisher.Mono;

public interface RequestPasswordResetNoAuthEventProcessor<R extends DomainEvent> extends EventProcessor<RequestPasswordResetNoAuthEvent, R> {

    @Override
    default Mono<R> process(RequestPasswordResetNoAuthEvent event) {
        return switch (event){
            case RequestPasswordResetNoAuthEvent.RequestPasswordResetNoAuthCreated e -> this.handle(e);
            case RequestPasswordResetNoAuthEvent.RequestPasswordResetNoAuthFailed e -> this.handle(e);
            case RequestPasswordResetNoAuthEvent.RequestPasswordResetNoAuthCompleted e -> this.handle(e);
        };
    }

    Mono<R> handle(RequestPasswordResetNoAuthEvent.RequestPasswordResetNoAuthCreated event);

    Mono<R> handle(RequestPasswordResetNoAuthEvent.RequestPasswordResetNoAuthFailed event);

    Mono<R> handle(RequestPasswordResetNoAuthEvent.RequestPasswordResetNoAuthCompleted event);

}
