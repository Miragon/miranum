package io.miragon.miranum.platform.engine.application.service;

import io.miragon.miranum.platform.engine.application.port.in.process.ProcessDefinitionQuery;
import io.miragon.miranum.platform.engine.application.port.out.process.MiranumProcessDefinitionPort;
import io.miragon.miranum.platform.engine.domain.process.MiranumProcessDefinition;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProcessDefinitionQueryService implements ProcessDefinitionQuery {

    private final MiranumProcessDefinitionPort miranumProcessDefinitionPort;

    @Override
    public List<MiranumProcessDefinition> getProcessDefinitions(
            String userId,
            List<String> groups,
            String query
    ) {
        final List<MiranumProcessDefinition> processDefinitions = miranumProcessDefinitionPort.getServiceDefinitions();

        final List<MiranumProcessDefinition> filteredDefinitions = filterByQuery(processDefinitions, query);

        final List<MiranumProcessDefinition> allowedToStart = filteredDefinitions.stream()
                .filter(obj -> miranumProcessDefinitionPort.allowedToStartDefinition(userId, groups, obj.getKey()))
                .toList();

        return allowedToStart;
    }

    private List<MiranumProcessDefinition> filterByQuery(
            final List<MiranumProcessDefinition> definitions,
            @Nullable final String query
    ) {
        final String lowerCaseQuery = query == null ? "" : query.toLowerCase();
        return lowerCaseQuery.isBlank()
                ? definitions
                : definitions.stream().filter(
                it ->
                        StringUtils.containsIgnoreCase(it.getKey(), lowerCaseQuery)
                                || StringUtils.containsIgnoreCase(it.getName(), lowerCaseQuery)
                                || StringUtils.containsIgnoreCase(it.getDescription(), lowerCaseQuery)
                                || StringUtils.containsIgnoreCase(it.getVersionTag(), lowerCaseQuery)
        ).collect(Collectors.toList());
    }
}
