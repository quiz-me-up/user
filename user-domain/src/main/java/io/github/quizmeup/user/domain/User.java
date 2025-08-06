package io.github.quizmeup.user.domain;

import io.github.quizmeup.sdk.eventflow.annotation.Aggregate;
import io.github.quizmeup.sdk.eventflow.annotation.AggregateIdentifier;
import io.github.quizmeup.sdk.eventflow.annotation.CommandHandler;
import io.github.quizmeup.sdk.eventflow.annotation.EventSourcingHandler;
import io.github.quizmeup.sdk.eventflow.core.domain.aggregate.AggregateLifecycle;
import io.github.quizmeup.user.domain.command.CreateUser;
import io.github.quizmeup.user.domain.command.DeleteUser;
import io.github.quizmeup.user.domain.event.UserCreated;
import io.github.quizmeup.user.domain.event.UserDeleted;
import io.github.quizmeup.user.domain.event.UserEvent;
import io.github.quizmeup.user.domain.exception.UserAlreadyExistException;
import io.github.quizmeup.user.domain.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

@Getter
@Aggregate
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    @AggregateIdentifier
    private String id;
    private String email;
    private String password;
    private LocalDateTime creationDate;

    @CommandHandler
    public UserEvent handle(CreateUser command) {
        if (StringUtils.isNotBlank(id)) {
            throw new UserAlreadyExistException(command.email());
        }

        return new UserCreated(command.id(), command.email(), command.password(), LocalDateTime.now());
    }

    @CommandHandler
    public UserEvent handle(DeleteUser command) {
        if (StringUtils.isBlank(command.id())) {
            throw new UserNotFoundException(command.id());
        }

        return new UserDeleted(command.id(), email);
    }

    @EventSourcingHandler
    public void apply(UserCreated event) {
        id = event.id();
        email = event.email();
        password = event.password();
    }

    @EventSourcingHandler
    public void apply(UserDeleted event) {
        AggregateLifecycle.markDeleted();
    }
}
