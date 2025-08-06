package io.github.quizmeup.user.infrastructure.model.request;

import jakarta.validation.constraints.Email;

public record DeleteUserRequest(@Email String email) {
}
