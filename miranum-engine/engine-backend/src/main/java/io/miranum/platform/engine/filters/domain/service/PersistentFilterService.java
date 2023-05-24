package io.miranum.platform.engine.filters.domain.service;

import io.miranum.platform.engine.filters.domain.mapper.FilterMapper;
import io.miranum.platform.engine.filters.domain.model.Filter;
import io.miranum.platform.engine.filters.infrastructure.repository.FilterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Service to handle filter objects in DigiWF.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PersistentFilterService {

    private final FilterRepository filterRepository;
    private final FilterMapper filterMapper;

    /**
     * Read filters of the user.
     *
     * @param userId the users id
     * @return list of user's filters
     */
    public List<Filter> getFilters(final String userId) {
        return this.filterRepository.findByUserId(userId)
                .stream()
                .map(this.filterMapper::map2Model)
                .collect(Collectors.toList());
    }

    /**
     * Get a specific filter of a user.
     *
     * @param filterId id of the filter
     * @param userId   id of the user
     * @return user's filter
     */
    public Filter findById(final String filterId, final String userId) {
        final Filter filter = this.filterRepository.findById(filterId)
                .map(this.filterMapper::map2Model)
                .orElseThrow(() -> new NoSuchElementException("filter (id=" + filterId + ")"));
        checkAccess(filter, userId);
        return filter;
    }

    /**
     * Delete a user's filter.
     *
     * @param filterId id of the filter to delete
     * @param userId   id of the user
     */
    public void deleteFilter(final String filterId, final String userId) {
        final Filter filter = findById(filterId, userId);
        checkAccess(filter, userId);
        this.filterRepository.deleteById(filterId);
    }

    /**
     * Save a user's filter.
     *
     * @param filter the filter to persist
     */
    public void saveFilter(final Filter filter) {
        if (!StringUtils.isEmpty(filter.getFilterString())) {
            filterRepository.save(filterMapper.map2Entity(filter));
        }
    }

    private void checkAccess(final Filter filter, final String userId) {
        if (!filter.getUserId().equals(userId)) {
            throw new RuntimeException(String.format("Filter with id %s not accessable", filter.getId()));
        }
    }

}
