package bj.gouv.sineb.choreography.events.email;

import bj.gouv.sineb.choreography.events.DomainEvent;
import bj.gouv.sineb.choreography.saga.auth.UserSaga;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

public sealed interface UserApprovalEvent extends DomainEvent, UserSaga {

    @Builder
    record UserApprovalCreated(UUID userId,
                              String email,
                              String tempPassword,
                              String firstname,
                              String lastname,
                              Instant createdAt) implements UserApprovalEvent {}

    @Builder
    record UserApprovalFailed(UUID userId,
                              String message,
                              Instant createdAt) implements UserApprovalEvent {}


    @Builder
    record UserApprovalCompleted(UUID userId,
                                 Instant createdAt) implements UserApprovalEvent {}

}
