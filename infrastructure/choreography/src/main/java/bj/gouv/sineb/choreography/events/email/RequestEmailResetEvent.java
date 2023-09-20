package bj.gouv.sineb.choreography.events.email;

import bj.gouv.sineb.choreography.events.DomainEvent;
import bj.gouv.sineb.choreography.saga.auth.UserSaga;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

public sealed interface RequestEmailResetEvent extends DomainEvent, UserSaga {

    @Builder
    record RequestEmailResetEventCreated(UUID userId,
                                         String code,
                                         String newEmail,
                                         String firstname,
                                         String lastname,
                                         Instant createdAt) implements RequestEmailResetEvent {}

    @Builder
    record RequestEmailResetEventFailed(UUID userId,
                                        String message,
                                        Instant createdAt) implements RequestEmailResetEvent {}


    @Builder
    record RequestEmailResetEventCompleted(UUID userId,
                                           Instant createdAt) implements RequestEmailResetEvent {}

}
