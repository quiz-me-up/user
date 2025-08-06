package io.github.quizmeup.user.domain.exception;

import io.github.quizmeup.sdk.eventflow.core.domain.exception.ConflictException;

public class UserAlreadyExistException extends ConflictException implements UserException {
    public UserAlreadyExistException(String email) {
        super(String.format("A User is already registered with email : %s", email));
    }
}
