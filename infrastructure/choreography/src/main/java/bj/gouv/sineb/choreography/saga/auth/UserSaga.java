package bj.gouv.sineb.choreography.saga.auth;

import bj.gouv.sineb.choreography.saga.Saga;

import java.util.UUID;

public interface UserSaga extends Saga {
    UUID userId();
}
