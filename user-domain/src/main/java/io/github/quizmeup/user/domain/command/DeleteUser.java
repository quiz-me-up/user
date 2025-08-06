package io.github.quizmeup.user.domain.command;

import io.github.quizmeup.sdk.eventflow.annotation.AggregateIdentifier;

public record DeleteUser(@AggregateIdentifier String id) implements UserCommand {
}
