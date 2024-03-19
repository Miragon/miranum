package io.miranum.platform.engine.application.service;

import io.miranum.platform.engine.application.port.in.process.ProcessInstanceQuery;
import io.miranum.platform.engine.application.port.out.process.MiranumProcessInstancePort;
import io.miranum.platform.engine.domain.process.MiranumProcessInstance;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service to interact with process instances.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MiranumProcessInstanceQueryService implements ProcessInstanceQuery {


    private final MiranumProcessInstancePort miranumProcessInstancePort;


    /**
     * Get all assigned  instances
     *
     * @return assigned  instances
     */
    public Page<MiranumProcessInstance> getProcessInstanceByUser(final String userId, int page, int size, String query) {
        val processInstances = this.miranumProcessInstancePort.getAllByUser(userId);
        val filteredDefinitions = filterByQuery(processInstances, query);
        return listToPage(filteredDefinitions, page, size);
    }


    private Page<MiranumProcessInstance> listToPage(
            final List<MiranumProcessInstance> definitions,
            final int page,
            final int size) {

        val from = page * size;
        val to = Math.min((page + 1) * size, definitions.size());
        val pageContent = definitions.subList(from, to);
        return new PageImpl<>(pageContent, PageRequest.of(page, size), definitions.size());
    }

    private List<MiranumProcessInstance> filterByQuery(
            final List<MiranumProcessInstance> definitions,
            @Nullable final String query
    ) {
        final String lowerCaseQuery = query == null ? "" : query.toLowerCase();
        return lowerCaseQuery.isBlank()
                ? definitions
                : definitions.stream().filter(
                it ->
                        StringUtils.containsIgnoreCase(it.getId(), lowerCaseQuery)
                                || StringUtils.containsIgnoreCase(it.getDescription(), lowerCaseQuery)
                                || StringUtils.containsIgnoreCase(it.getDefinitionKey(), lowerCaseQuery)
                                || StringUtils.containsIgnoreCase(it.getDefinitionName(), lowerCaseQuery)
                                || StringUtils.containsIgnoreCase(it.getStatusKey(), lowerCaseQuery)
                                || StringUtils.containsIgnoreCase(it.getStatus(), lowerCaseQuery)
                                || StringUtils.containsIgnoreCase(it.getStartTime() != null ? it.getStartTime().toString() : "", lowerCaseQuery)
                                || StringUtils.containsIgnoreCase(it.getEndTime() != null ? it.getEndTime().toString() : "", lowerCaseQuery)
                                || StringUtils.containsIgnoreCase(it.getRemovalTime() != null ? it.getRemovalTime().toString() : "", lowerCaseQuery)
        ).collect(Collectors.toList());
    }

}
