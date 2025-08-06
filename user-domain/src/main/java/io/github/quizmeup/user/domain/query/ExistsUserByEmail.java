package io.github.quizmeup.user.domain.query;

public record ExistsUserByEmail(String email) implements UserQuery {
}
