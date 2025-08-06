package io.github.quizmeup.user.infrastructure.repository;

import io.github.quizmeup.user.infrastructure.entity.MongoUserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MongoUserRepository extends MongoRepository<MongoUserEntity, String> {

    Optional<MongoUserEntity> findByEmailIgnoreCase(String email);

    void deleteByEmail(String email);

    boolean existsByEmail(String email);
}
