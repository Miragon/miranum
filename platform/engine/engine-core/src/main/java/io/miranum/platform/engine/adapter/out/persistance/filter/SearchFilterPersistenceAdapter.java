package io.miranum.platform.engine.adapter.out.persistance.filter;

import io.miranum.platform.engine.application.port.out.filter.SearchFilterPort;
import io.miranum.platform.engine.domain.filter.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class SearchFilterPersistenceAdapter implements SearchFilterPort {

    private final FilterRepository filterRepository;
    private final FilterMapper filterMapper;

    @Override
    public List<Filter> getAllByUserId(final String userId) {
        return this.filterRepository.findByUserId(userId)
                .stream()
                .map(this.filterMapper::map2Model)
                .collect(Collectors.toList());
    }

    @Override
    public Filter findById(String filterId) {
        return this.filterRepository.findById(filterId)
                .map(this.filterMapper::map2Model)
                .orElseThrow(() -> new RuntimeException("Filter with id " + filterId + " not found"));
    }

    @Override
    public void deleteById(final String filterId) {
        this.filterRepository.deleteById(filterId);
    }

    @Override
    public void createFilter(final Filter filter) {
        filterRepository.save(filterMapper.map2Entity(filter));
    }
}
