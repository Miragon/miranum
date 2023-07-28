package io.miranum.platform.engine.application.port.out.filter;

import io.miranum.platform.engine.domain.filter.Filter;

import java.util.List;

public interface SearchFilterPort {

    void deleteById(String filterId);

    List<Filter> getAllByUserId(String userId);

    Filter findById(String filterId);

    void createFilter(Filter filter);
}
