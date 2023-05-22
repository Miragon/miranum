package io.miranum.platform.engine.processdefinition.domain.facade;

import io.miranum.platform.engine.jsonschema.domain.model.JsonSchema;
import io.miranum.platform.engine.jsonschema.domain.service.JsonSchemaService;
import io.miranum.platform.engine.processdefinition.domain.model.ServiceDefinition;
import io.miranum.platform.engine.processdefinition.domain.model.ServiceDefinitionDetail;
import io.miranum.platform.engine.processdefinition.domain.service.ServiceDefinitionAuthService;
import io.miranum.platform.engine.processdefinition.domain.service.ServiceDefinitionDataService;
import io.miranum.platform.engine.processdefinition.domain.service.ServiceDefinitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Component
@RequiredArgsConstructor
public class ServiceDefinitionFacade {

    private final ServiceDefinitionService serviceDefinitionService;
    private final ServiceDefinitionAuthService serviceDefinitionAuthService;
    private final ServiceDefinitionDataService serviceDefinitionDataService;

    private final JsonSchemaService jsonSchemaService;

    private final org.camunda.bpm.engine.FormService camundaFormService;

    public void startInstance(final String key, final Map<String, Object> variables, final String userId, final List<String> groups) {
        this.startInstance(key, null, null, variables, userId, groups);
    }

    public void startInstance(final String key, final String businessKey, final Map<String, Object> variables, final String userId, final List<String> groups) {
        this.startInstance(key, businessKey, null, variables, userId, groups);
    }

    public void startInstance(final String key, final String businessKey, final String fileContext, final Map<String, Object> variables, final String userId, final List<String> groups) {
        final ServiceDefinitionDetail definition = this.getServiceDefinitionDetailAuthorized(key, userId, groups);
        final Map<String, Object> serializedVariables = this.serviceDefinitionDataService.serializeVariables(definition, variables);

        // get the s3-integrations topic or use the default one from application properties
        // and set it as a variable on process start


        this.serviceDefinitionService.startInstance(definition, businessKey, serializedVariables, userId);
    }

    public ServiceDefinitionDetail getServiceDefinitionDetail(final String key, final String userId, final List<String> groups) {
        if (!this.serviceDefinitionAuthService.allowedToStartDefinition(userId, groups, key)) {
            throw new RuntimeException(String.format("Service definition not accessible: %s", key));
        }

        final ServiceDefinitionDetail detail = this.serviceDefinitionService.getServiceDefinitionDetail(key, userId, groups);
        this.addFormData(detail);
        return detail;
    }


    public List<ServiceDefinition> getStartableServiceDefinitions(final String userId, final List<String> groups) {
        final List<ServiceDefinition> serviceDefinitions = this.serviceDefinitionService.getServiceDefinitions();
        return serviceDefinitions.stream()
                .filter(definition -> this.serviceDefinitionAuthService.allowedToStartDefinition(userId, groups, definition.getKey()))
                .collect(Collectors.toList());
    }

    //--------------------------------------- Helper Methods ---------------------------------------//

    private void addFormData(final ServiceDefinitionDetail detail) {
        final String formKey = this.camundaFormService.getStartFormKey(detail.getId());
        final Optional<JsonSchema> schema = this.jsonSchemaService.getByKey(formKey);
        schema.map(JsonSchema::getSchemaMap).ifPresent(detail::setJsonSchema);
    }

    private ServiceDefinitionDetail getServiceDefinitionDetailAuthorized(final String key, final String userId, final List<String> groups) {
        if (!this.serviceDefinitionAuthService.allowedToStartDefinition(userId, groups, key)) {
            throw new RuntimeException(String.format("Service definition not accessible: %s", key));
        }

        final ServiceDefinitionDetail detail = this.serviceDefinitionService.getServiceDefinitionDetail(key, userId, groups);
        this.addFormData(detail);
        return detail;
    }

}
