package io.github.quizmeup.user.infrastructure.config;

import io.github.quizmeup.sdk.eventflow.core.domain.handler.Handler;
import io.github.quizmeup.sdk.eventflow.core.usecase.*;
import io.github.quizmeup.user.application.handler.UserEventHandler;
import io.github.quizmeup.user.application.handler.UserQueryHandler;
import io.github.quizmeup.user.application.service.UserService;
import io.github.quizmeup.user.application.usecase.UserUseCase;
import io.github.quizmeup.user.domain.User;
import io.github.quizmeup.user.infrastructure.adapter.MongoUserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.Collection;


@Configuration
@RequiredArgsConstructor
public class UserUseCaseConfig {

    private final ScanClass scanClass;
    private final ScanObject scanObject;
    private final RegisterHandler registerHandler;

    @Bean
    public UserUseCase userUseCase(final SendQuery sendQuery,
                                   final SendCommand sendCommand) {
        return new UserService(sendQuery, sendCommand);
    }

    @Bean
    public UserEventHandler userEventHandler(final MongoUserStore mongoUserStore){
        final UserEventHandler userEventHandler = new UserEventHandler(mongoUserStore);
        final Collection<Handler> handlers = scanObject.scan(userEventHandler);
        handlers.forEach(registerHandler::register);
        return userEventHandler;
    }

    @Bean
    public UserQueryHandler userQueryHandler(final MongoUserStore mongoUserStore){
        final UserQueryHandler userQueryHandler =  new UserQueryHandler(mongoUserStore);
        final Collection<Handler> handlers = scanObject.scan(userQueryHandler);
        handlers.forEach(registerHandler::register);
        return userQueryHandler;
    }

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent ignored) {
        final Collection<Handler> handlers = scanClass.scan(User.class);
        handlers.forEach(registerHandler::register);
    }
}
