package io.miranum.platform.engine.application.service;

import io.miranum.platform.engine.application.port.in.process.ProcessDefinitionQuery;
import io.miranum.platform.engine.application.port.out.process.MiranumProcessDefinitionPort;
import io.miranum.platform.engine.domain.process.MiranumProcessDefinition;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProcessDefinitionQueryService implements ProcessDefinitionQuery {

    private final MiranumProcessDefinitionPort miranumProcessDefinitionPort;

    @Override
    public Page<MiranumProcessDefinition> getProcessDefinitions(
            String userId,
            List<String> groups,
            int page,
            int size,
            String query
    ) {
        final List<MiranumProcessDefinition> processDefinitions = miranumProcessDefinitionPort.getServiceDefinitions();

        final List<MiranumProcessDefinition> filteredDefinitions = filterByQuery(processDefinitions, query);

        final List<MiranumProcessDefinition> allowedToStart = filteredDefinitions.stream()
                .filter(obj -> miranumProcessDefinitionPort.allowedToStartDefinition(userId, groups, obj.getKey()))
                .toList();

        return listToPage(allowedToStart, page, size);
    }

    private Page<MiranumProcessDefinition> listToPage(
            final List<MiranumProcessDefinition> definitions,
            final int page,
            final int size) {

        val from = page * size;
        val to = Math.min((page + 1) * size, definitions.size());
        val pageContent = definitions.subList(from, to);
        return new PageImpl<>(pageContent, PageRequest.of(page, size), definitions.size());
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
