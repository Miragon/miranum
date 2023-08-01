package io.miranum.platform.engine.adapter.in.web.filter;

import io.miranum.platform.engine.api.AppAuthenticationProvider;
import io.miranum.platform.engine.application.port.in.filter.CreateFilterUseCase;
import io.miranum.platform.engine.application.port.in.filter.DeleteFilterUseCase;
import io.miranum.platform.engine.application.port.in.filter.SearchFilterQuery;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * API to load user filters and perform actions on them.
 */
@RestController
@Transactional
@RequestMapping("/rest/filter")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "FilterRestController", description = "API to load user filters and perform actions on them")
public class FiltersRestController {

    private final SearchFilterQuery filterQuery;
    private final CreateFilterUseCase createFilterUseCase;
    private final DeleteFilterUseCase deleteFilterUseCase;
    private final AppAuthenticationProvider authenticationProvider;
    private final FilterApiMapper filterApiMapper;

    /**
     * Returns all filters of the authenticated user and page.
     *
     * @return filters
     */
    @GetMapping
    public ResponseEntity<List<FilterDto>> getFilters() {
        log.debug("getFilters");
        val filters = this.filterQuery.getAllFilters(this.authenticationProvider.getCurrentUserId());
        return ResponseEntity.ok(this.filterApiMapper.map2TO(filters));
    }

    /**
     * Saves the given filter.
     *
     * @param dto Data of the filter
     * @return the saved filter
     */
    @PutMapping
    public ResponseEntity<FilterDto> saveFilter(@Valid @RequestBody final SaveFilterDto dto) {
        this.createFilterUseCase.createFilter(filterApiMapper.map(dto, this.authenticationProvider.getCurrentUserId()));
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes the filter.
     *
     * @param filterId Id of the filter
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final String filterId) {
        this.deleteFilterUseCase.deleteFilter(filterId, this.authenticationProvider.getCurrentUserId());
        return ResponseEntity.ok().build();
    }

}

