package io.miranum.platform.engine.filters.domain.mapper;

import io.miranum.platform.engine.filters.domain.model.Filter;
import io.miranum.platform.engine.filters.infrastructure.entity.FilterEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Map btw. {@link Filter} and {@link FilterEntity}.
 */
@Mapper
public interface FilterMapper {

    List<Filter> map2Model(List<FilterEntity> list);

    Filter map2Model(FilterEntity list);

    FilterEntity map2Entity(Filter filter);

}
