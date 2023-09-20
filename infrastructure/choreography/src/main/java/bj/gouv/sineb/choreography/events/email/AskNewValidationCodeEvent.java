package bj.gouv.sineb.choreography.events.email;

import bj.gouv.sineb.choreography.events.DomainEvent;
import bj.gouv.sineb.choreography.saga.auth.UserSaga;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

public sealed interface AskNewValidationCodeEvent extends DomainEvent, UserSaga {

    @Builder
    record AskNewNewValidationCodeCreated(UUID userId,
                                          String code,
                                          String email,
                                          String firstname,
                                          String lastname,
                                          Instant createdAt) implements AskNewValidationCodeEvent {}

    @Builder
    record AskNewNewValidationCodeFailed(UUID userId,
                                         String message,
                                         Instant createdAt) implements AskNewValidationCodeEvent {}


    @Builder
    record AskNewNewValidationCodeCompleted(UUID userId,
                                            Instant createdAt) implements AskNewValidationCodeEvent {}

}
