package io.miragon.miranum.platform.engine.application.service;

import io.miragon.miranum.platform.engine.application.port.in.process.ProcessInstanceQuery;
import io.miragon.miranum.platform.engine.application.port.out.process.MiranumProcessInstancePort;
import io.miragon.miranum.platform.engine.domain.process.MiranumProcessInstance;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
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
    public List<MiranumProcessInstance> getProcessInstanceByUser(final String userId, String query) {
        val processInstances = this.miranumProcessInstancePort.getAllByUser(userId);
        return filterByQuery(processInstances, query);
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
