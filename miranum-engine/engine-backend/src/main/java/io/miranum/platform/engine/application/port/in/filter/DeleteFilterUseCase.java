package io.miranum.platform.engine.application.port.in.filter;

public interface DeleteFilterUseCase {

    void deleteFilter(final String filterId, final String userId);
}
