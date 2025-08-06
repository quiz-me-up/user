package io.github.quizmeup.user.domain.query;

import io.github.quizmeup.sdk.eventflow.core.domain.pagination.PageRequest;
import io.github.quizmeup.sdk.eventflow.core.domain.pagination.SortDetails;

public record FindAllUser(int pageNumber, int pageSize, SortDetails sort) implements UserQuery, PageRequest {
}
