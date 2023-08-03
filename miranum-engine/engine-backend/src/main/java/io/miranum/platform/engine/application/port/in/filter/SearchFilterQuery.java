package io.miranum.platform.engine.application.port.in.filter;

import io.miranum.platform.engine.domain.filter.Filter;

import java.util.List;

public interface SearchFilterQuery {

    List<Filter> getAllFilters(final String userId);

}
