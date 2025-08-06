package io.github.quizmeup.user.infrastructure.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * MongoDB entity for storing user data.
 * This class represents the user data in the database.
 */
@Data
@Builder(toBuilder = true)
@Document(collection = "users")
@EqualsAndHashCode(of = "id", callSuper = false)
public class MongoUserEntity  {

    @Id
    private String id;
    @Indexed(unique = true)
    private String email;
    private String password;
    private LocalDateTime creationDate;
}
