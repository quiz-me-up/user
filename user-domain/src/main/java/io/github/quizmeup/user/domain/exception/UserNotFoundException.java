package io.github.quizmeup.user.domain.exception;

import io.github.quizmeup.sdk.eventflow.core.domain.exception.ResourceNotFoundException;
import io.github.quizmeup.user.domain.query.FindUserByEmail;
import io.github.quizmeup.user.domain.query.FindUserById;

public class UserNotFoundException extends ResourceNotFoundException implements UserException {

    public UserNotFoundException(FindUserByEmail findUserByEmail) {
        super(String.format("No User found for email : %s", findUserByEmail.email()));
    }

    public UserNotFoundException(FindUserById findUserById) {
        super(String.format("No User found for id : %s", findUserById.id()));
    }

    public UserNotFoundException(String id) {
        super(String.format("No User found for id : %s", id));
    }
}
