package io.github.quizmeup.user.infrastructure.resource;

import io.github.quizmeup.sdk.eventflow.core.domain.pagination.Order;
import io.github.quizmeup.sdk.eventflow.core.domain.pagination.Page;
import io.github.quizmeup.sdk.eventflow.core.domain.pagination.SortDetails;
import io.github.quizmeup.sdk.common.domain.response.ActionType;
import io.github.quizmeup.sdk.common.domain.response.AggregateRelatedMessageResponse;
import io.github.quizmeup.sdk.common.domain.response.MessageResponse;
import io.github.quizmeup.user.application.usecase.UserUseCase;
import io.github.quizmeup.user.domain.User;
import io.github.quizmeup.user.domain.command.CreateUser;
import io.github.quizmeup.user.domain.command.DeleteUser;
import io.github.quizmeup.user.domain.query.FindAllUser;
import io.github.quizmeup.user.domain.query.FindUserByEmail;
import io.github.quizmeup.user.domain.query.FindUserById;
import io.github.quizmeup.user.infrastructure.model.request.CreateUserRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class UserResource {

    private final UserUseCase userUseCase;
    private final PasswordEncoder passwordEncoder;
    public static final String USER_CREATION_ENDPOINT = "/api/v1/users";

    @PostMapping("/users")
    public CompletableFuture<MessageResponse> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        final String passwordEncoded = passwordEncoder.encode(createUserRequest.password());
        final CreateUser createUserCommand = new CreateUser(UUID.randomUUID().toString(), createUserRequest.email(), passwordEncoded);
        return userUseCase.create(createUserCommand)
                .thenApplyAsync(aggregateId -> new AggregateRelatedMessageResponse(aggregateId, User.class, ActionType.CREATE));
    }

    @GetMapping("/users")
    public CompletableFuture<Page<User>> findAllUser(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                                     @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        final FindAllUser findAllUser = new FindAllUser(pageNumber, pageSize, SortDetails.by(Order.Direction.ASC, "email"));
        return userUseCase.findAll(findAllUser);
    }

    @DeleteMapping("/users/{id}")
    public CompletableFuture<MessageResponse> deleteUserById(@PathVariable(value = "id") @Valid @NotBlank String id) {
        final DeleteUser deleteUserCommand = new DeleteUser(id);
        return userUseCase.delete(deleteUserCommand)
                .thenApplyAsync(aggregateId -> new AggregateRelatedMessageResponse(id, User.class, ActionType.DELETE));
    }

    @GetMapping("/users/{id}")
    public CompletableFuture<User> findUserById(@PathVariable(value = "id") @Valid @NotBlank String id) {
        final FindUserById findUserById = new FindUserById(id);
        return userUseCase.getById(findUserById);
    }

    @GetMapping("/users-by-email/{email}")
    public CompletableFuture<User> findUserByEmail(@PathVariable(value = "email") @Valid @NotBlank @Email String email) {
        final FindUserByEmail findUserByEmail = new FindUserByEmail(email);
        return userUseCase.getByEmail(findUserByEmail);
    }
}
