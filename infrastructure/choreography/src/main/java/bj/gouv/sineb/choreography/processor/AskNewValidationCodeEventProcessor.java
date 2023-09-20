package bj.gouv.sineb.choreography.processor;

import bj.gouv.sineb.choreography.events.DomainEvent;
import bj.gouv.sineb.choreography.events.email.AskNewValidationCodeEvent;
import bj.gouv.sineb.choreography.events.email.UserApprovalEvent;
import reactor.core.publisher.Mono;

public interface AskNewValidationCodeEventProcessor<R extends DomainEvent> extends EventProcessor<AskNewValidationCodeEvent, R> {

    @Override
    default Mono<R> process(AskNewValidationCodeEvent event) {
        return switch (event){
            case AskNewValidationCodeEvent.AskNewNewValidationCodeCreated e -> this.handle(e);
            case AskNewValidationCodeEvent.AskNewNewValidationCodeFailed e -> this.handle(e);
            case AskNewValidationCodeEvent.AskNewNewValidationCodeCompleted e -> this.handle(e);
        };
    }

    Mono<R> handle(AskNewValidationCodeEvent.AskNewNewValidationCodeCreated event);

    Mono<R> handle(AskNewValidationCodeEvent.AskNewNewValidationCodeFailed event);

    Mono<R> handle(AskNewValidationCodeEvent.AskNewNewValidationCodeCompleted event);

}
