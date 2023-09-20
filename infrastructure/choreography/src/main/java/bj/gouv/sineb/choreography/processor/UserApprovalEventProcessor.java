package bj.gouv.sineb.choreography.processor;

import bj.gouv.sineb.choreography.events.DomainEvent;
import bj.gouv.sineb.choreography.events.email.UserApprovalEvent;
import reactor.core.publisher.Mono;

public interface UserApprovalEventProcessor<R extends DomainEvent> extends EventProcessor<UserApprovalEvent, R> {

    @Override
    default Mono<R> process(UserApprovalEvent event) {
        return switch (event){
            case UserApprovalEvent.UserApprovalCreated e -> this.handle(e);
            case UserApprovalEvent.UserApprovalFailed e -> this.handle(e);
            case UserApprovalEvent.UserApprovalCompleted e -> this.handle(e);
        };
    }

    Mono<R> handle(UserApprovalEvent.UserApprovalCreated event);

    Mono<R> handle(UserApprovalEvent.UserApprovalFailed event);

    Mono<R> handle(UserApprovalEvent.UserApprovalCompleted event);

}
