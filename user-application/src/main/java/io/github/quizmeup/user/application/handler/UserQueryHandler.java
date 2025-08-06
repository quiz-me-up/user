package io.github.quizmeup.user.application.handler;

import io.github.quizmeup.sdk.eventflow.annotation.QueryHandler;
import io.github.quizmeup.sdk.eventflow.core.domain.pagination.Page;
import io.github.quizmeup.user.application.spi.UserStore;
import io.github.quizmeup.user.domain.User;
import io.github.quizmeup.user.domain.query.ExistsUserByEmail;
import io.github.quizmeup.user.domain.query.FindAllUser;
import io.github.quizmeup.user.domain.query.FindUserByEmail;
import io.github.quizmeup.user.domain.query.FindUserById;

import java.util.Optional;

public class UserQueryHandler {

    private final UserStore userStore;

    public UserQueryHandler(UserStore userStore) {
        this.userStore = userStore;
    }

    @QueryHandler
    public Page<User> handle(FindAllUser query) {
        return userStore.findAll(query);
    }

    @QueryHandler
    public Optional<User> handle(FindUserById query) {
        return userStore.findById(query.id());
    }

    @QueryHandler
    public Optional<User> handle(FindUserByEmail query) {
        return userStore.findByEmail(query.email());
    }

    @QueryHandler
    public Boolean handle(ExistsUserByEmail query) {
        return userStore.existsByEmail(query.email());
    }
}
