package io.github.quizmeup.user.application.handler;

import io.github.quizmeup.sdk.eventflow.annotation.EventHandler;
import io.github.quizmeup.user.application.spi.UserStore;
import io.github.quizmeup.user.domain.User;
import io.github.quizmeup.user.domain.event.UserCreated;
import io.github.quizmeup.user.domain.event.UserDeleted;

/**
 * Event handler for user domain events.
 * This class is responsible for handling all user-related events and updating the user store accordingly.
 */
public class UserEventHandler {

    private final UserStore userStore;

    public UserEventHandler(UserStore userStore) {
        this.userStore = userStore;
    }

    /**
     * Handles the UserCreatedEvent by creating a new user in the store.
     *
     * @param event the event containing user creation data
     */
    @EventHandler
    public void on(UserCreated event) {
        final User user = User.builder()
                .id(event.id())
                .email(event.email())
                .password(event.password())
                .creationDate(event.creationDate())
                .build();
        userStore.save(user);
    }

    /**
     * Handles the UserDeletedEvent by removing the user from the store.
     *
     * @param event the event containing user deletion data
     */
    @EventHandler
    public void on(UserDeleted event) {
        userStore.deleteById(event.id());
    }
}
