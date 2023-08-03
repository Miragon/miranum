package io.miranum.platform.engine.adapter.in.web.filter;

import io.miranum.platform.engine.domain.filter.Filter;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Map {@link Filter} domain object into {@link FilterDto}, {@link SaveFilterDto} transport object.
 */
@Mapper
public interface FilterApiMapper {

    List<FilterDto> map2TO(List<Filter> list);

    FilterDto map2TO(Filter filter);

    Filter map(FilterDto filterTO);

    Filter map(SaveFilterDto filterTO, String userId);

}
