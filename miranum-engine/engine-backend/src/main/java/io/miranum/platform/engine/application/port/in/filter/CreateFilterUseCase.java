package io.miranum.platform.engine.application.port.in.filter;

import io.miranum.platform.engine.domain.filter.Filter;

public interface CreateFilterUseCase {

    void createFilter(final Filter filter);
}
