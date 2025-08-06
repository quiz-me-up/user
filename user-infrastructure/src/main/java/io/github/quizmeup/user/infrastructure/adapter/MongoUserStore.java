package io.github.quizmeup.user.infrastructure.adapter;

import io.github.quizmeup.sdk.eventflow.core.domain.pagination.Page;
import io.github.quizmeup.sdk.eventflow.core.domain.pagination.PageRequest;
import io.github.quizmeup.sdk.eventflow.spring.starter.utils.SpringAdaptors;
import io.github.quizmeup.user.application.spi.UserStore;
import io.github.quizmeup.user.domain.User;
import io.github.quizmeup.user.infrastructure.mapper.UserMapper;
import io.github.quizmeup.user.infrastructure.repository.MongoUserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class MongoUserStore implements UserStore {

    private final MongoUserRepository mongoUserRepository;

    @Override
    public void save(User user) {
        Optional.ofNullable(user)
                .map(UserMapper::toEntity)
                .ifPresent(mongoUserRepository::save);
    }

    @Override
    public void deleteById(String identifier) {
        Optional.ofNullable(identifier)
                .filter(StringUtils::isNotBlank)
                .ifPresent(mongoUserRepository::deleteById);
    }

    @Override
    public boolean existsById(String identifier) {
        return mongoUserRepository.existsById(identifier);
    }

    @Override
    public Optional<User> findById(String identifier) {
        return Optional.ofNullable(identifier)
                .filter(StringUtils::isNotBlank)
                .flatMap(mongoUserRepository::findById)
                .map(UserMapper::toDomain);
    }

    @Override
    public Page<User> findAll(PageRequest pageRequest) {
        return Optional.ofNullable(pageRequest)
                .map(SpringAdaptors::toPageable)
                .map(mongoUserRepository::findAll)
                .map(springPage -> SpringAdaptors.toPage(springPage, UserMapper::toDomain))
                .orElse(null);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(email)
                .filter(StringUtils::isNotBlank)
                .flatMap(mongoUserRepository::findByEmailIgnoreCase)
                .map(UserMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return mongoUserRepository.existsByEmail(email);
    }
}
