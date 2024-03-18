package io.miranum.platform.engine.application.service;

import io.miranum.platform.engine.application.port.in.filter.CreateFilterUseCase;
import io.miranum.platform.engine.application.port.in.filter.DeleteFilterUseCase;
import io.miranum.platform.engine.application.port.in.filter.SearchFilterQuery;
import io.miranum.platform.engine.application.port.out.filter.SearchFilterPort;
import io.miranum.platform.engine.domain.filter.Filter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class SearchFilterService implements SearchFilterQuery, CreateFilterUseCase, DeleteFilterUseCase {

    private final SearchFilterPort searchFilterPort;

    @Override
    public List<Filter> getAllFilters(final String userId) {
        return searchFilterPort.getAllByUserId(userId);
    }

    @Override
    public void deleteFilter(final String filterId, final String userId) {
        final Filter filter = searchFilterPort.findById(filterId);
        checkAccess(filter, userId);
        this.searchFilterPort.deleteById(filterId);
    }

    @Override
    public void createFilter(final Filter filter) {
        if (!StringUtils.isEmpty(filter.getFilterString())) {
            searchFilterPort.createFilter(filter);
        }
    }

    private void checkAccess(final Filter filter, final String userId) {
        if (!filter.getUserId().equals(userId)) {
            throw new RuntimeException(String.format("Filter with id %s not accessable", filter.getId()));
        }
    }

}
