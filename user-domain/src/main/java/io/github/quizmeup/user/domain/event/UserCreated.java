package io.github.quizmeup.user.domain.event;

import io.github.quizmeup.sdk.eventflow.annotation.AggregateIdentifier;

import java.time.LocalDateTime;

public record UserCreated(@AggregateIdentifier String id, String email, String password, LocalDateTime creationDate) implements UserEvent {
}
