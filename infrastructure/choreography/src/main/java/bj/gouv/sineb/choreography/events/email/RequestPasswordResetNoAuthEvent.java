package bj.gouv.sineb.choreography.events.email;

import bj.gouv.sineb.choreography.events.DomainEvent;
import bj.gouv.sineb.choreography.saga.auth.UserSaga;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

public sealed interface RequestPasswordResetNoAuthEvent extends DomainEvent, UserSaga {

    @Builder
    record RequestPasswordResetNoAuthCreated(UUID userId,
                                             String code,
                                             String email,
                                             String firstname,
                                             String lastname,
                                             Instant createdAt) implements RequestPasswordResetNoAuthEvent {}

    @Builder
    record RequestPasswordResetNoAuthFailed(UUID userId,
                                            String message,
                                            Instant createdAt) implements RequestPasswordResetNoAuthEvent {}


    @Builder
    record RequestPasswordResetNoAuthCompleted(UUID userId,
                                               Instant createdAt) implements RequestPasswordResetNoAuthEvent {}
}
