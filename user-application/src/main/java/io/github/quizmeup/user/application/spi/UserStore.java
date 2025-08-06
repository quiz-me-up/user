package io.github.quizmeup.user.application.spi;

import io.github.quizmeup.sdk.common.application.store.CrudStore;
import io.github.quizmeup.user.domain.User;

import java.util.Optional;

public interface UserStore extends CrudStore<User, String> {

    default String resourceName() {
        return User.class.getSimpleName();
    }

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
