package io.github.quizmeup.user.application.service;

import io.github.quizmeup.sdk.eventflow.core.domain.pagination.Page;
import io.github.quizmeup.sdk.eventflow.core.domain.response.ResponseType;
import io.github.quizmeup.sdk.eventflow.core.usecase.SendCommand;
import io.github.quizmeup.sdk.eventflow.core.usecase.SendQuery;
import io.github.quizmeup.user.application.usecase.UserUseCase;
import io.github.quizmeup.user.domain.User;
import io.github.quizmeup.user.domain.command.CreateUser;
import io.github.quizmeup.user.domain.command.DeleteUser;
import io.github.quizmeup.user.domain.exception.UserAlreadyExistException;
import io.github.quizmeup.user.domain.query.ExistsUserByEmail;
import io.github.quizmeup.user.domain.query.FindAllUser;
import io.github.quizmeup.user.domain.query.FindUserByEmail;
import io.github.quizmeup.user.domain.query.FindUserById;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Implementation of the UserUseCase interface.
 * This service is responsible for handling user-related commands and queries.
 */
public class UserService implements UserUseCase {

    private final SendQuery sendQuery;
    private final SendCommand sendCommand;
    public UserService(SendQuery sendQuery,
                       SendCommand sendCommand) {
        this.sendQuery = sendQuery;
        this.sendCommand = sendCommand;
    }
    @Override
    public CompletableFuture<String> create(CreateUser command) {
        return existsByEmail(new ExistsUserByEmail(command.email()))
                .thenComposeAsync(existsUserByEmail -> {
                    if (existsUserByEmail) {
                        throw new UserAlreadyExistException(command.email());
                    } else {
                        return sendCommand.send(command);
                    }
                });
    }
    @Override
    public CompletableFuture<String> delete(DeleteUser command) {
        return sendCommand.send(command);
    }

    @Override
    public CompletableFuture<Boolean> existsByEmail(ExistsUserByEmail query) {
        return sendQuery.send(query, ResponseType.instanceOf(Boolean.class));
    }

    @Override
    public CompletableFuture<Optional<User>> findByEmail(FindUserByEmail query) {
        return sendQuery.send(query, ResponseType.optionalOf(User.class));
    }

    @Override
    public CompletableFuture<Optional<User>> findById(FindUserById query) {
        return sendQuery.send(query, ResponseType.optionalOf(User.class));
    }

    @Override
    public CompletableFuture<Page<User>> findAll(FindAllUser query) {
        return sendQuery.send(query, ResponseType.pageOf(User.class));
    }
}
