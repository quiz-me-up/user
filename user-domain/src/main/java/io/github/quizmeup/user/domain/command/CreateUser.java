package io.github.quizmeup.user.domain.command;

import io.github.quizmeup.sdk.eventflow.annotation.AggregateIdentifier;

public record CreateUser(@AggregateIdentifier String id, String email, String password) implements UserCommand {
}
