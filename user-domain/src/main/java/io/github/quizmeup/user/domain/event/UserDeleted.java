package io.github.quizmeup.user.domain.event;

import io.github.quizmeup.sdk.eventflow.annotation.AggregateIdentifier;

public record UserDeleted(@AggregateIdentifier String id, String email) implements UserEvent {
}
