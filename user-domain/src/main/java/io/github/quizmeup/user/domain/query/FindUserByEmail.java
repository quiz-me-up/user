package io.github.quizmeup.user.domain.query;

public record FindUserByEmail(String email) implements UserQuery {
}
