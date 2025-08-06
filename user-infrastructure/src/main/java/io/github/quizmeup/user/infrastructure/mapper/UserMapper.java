package io.github.quizmeup.user.infrastructure.mapper;

import io.github.quizmeup.user.domain.User;
import io.github.quizmeup.user.infrastructure.entity.MongoUserEntity;

import static java.util.Objects.isNull;

/**
 * Mapper class for converting between User domain objects and MongoUserEntity database objects.
 * This class follows the mapper pattern to separate the domain model from the infrastructure layer.
 */
public class UserMapper {

    /**
     * Converts a User domain object to a MongoUserEntity database object.
     *
     * @param user the User domain object to convert
     * @return the corresponding MongoUserEntity database object
     */
    public static MongoUserEntity toEntity(User user) {
        if (isNull(user)) {
            return null;
        }

        return MongoUserEntity.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .creationDate(user.getCreationDate())
                .build();
    }

    /**
     * Converts a MongoUserEntity database object to a User domain object.
     *
     * @param entity the MongoUserEntity database object to convert
     * @return the corresponding User domain object
     */
    public static User toDomain(MongoUserEntity entity) {
        if (isNull(entity)) {
            return null;
        }

        return User.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .creationDate(entity.getCreationDate())
                .build();
    }

}
