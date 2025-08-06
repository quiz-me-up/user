package io.github.quizmeup.user.application.usecase;

import io.github.quizmeup.sdk.eventflow.core.domain.pagination.Page;
import io.github.quizmeup.user.domain.User;
import io.github.quizmeup.user.domain.command.CreateUser;
import io.github.quizmeup.user.domain.command.DeleteUser;
import io.github.quizmeup.user.domain.exception.UserNotFoundException;
import io.github.quizmeup.user.domain.query.ExistsUserByEmail;
import io.github.quizmeup.user.domain.query.FindAllUser;
import io.github.quizmeup.user.domain.query.FindUserByEmail;
import io.github.quizmeup.user.domain.query.FindUserById;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface UserUseCase {

    CompletableFuture<String> create(CreateUser command);

    CompletableFuture<String> delete(DeleteUser command);

    CompletableFuture<Boolean> existsByEmail(ExistsUserByEmail query);

    CompletableFuture<Optional<User>> findByEmail(FindUserByEmail query);

    CompletableFuture<Optional<User>> findById(FindUserById query);

    CompletableFuture<Page<User>> findAll(FindAllUser query);

    default CompletableFuture<User> getById(FindUserById query){
        return findById(query).thenApplyAsync(optionalUser -> optionalUser.orElseThrow(() -> new UserNotFoundException(query)));
    }

    default CompletableFuture<User> getByEmail(FindUserByEmail query){
        return findByEmail(query).thenApplyAsync(optionalUser -> optionalUser.orElseThrow(() -> new UserNotFoundException(query)));
    }
}
