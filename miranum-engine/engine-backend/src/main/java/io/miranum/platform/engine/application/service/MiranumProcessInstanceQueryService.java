package io.miranum.platform.engine.application.service;

import io.miranum.platform.engine.application.port.in.process.MiranumProcessInstanceQuery;
import io.miranum.platform.engine.application.port.out.engine.ServiceInstanceVariablePort;
import io.miranum.platform.engine.application.port.out.history.GetHistoricTasksByInstanceIdPort;
import io.miranum.platform.engine.application.port.out.process.MiranumProcessInstancePort;
import io.miranum.platform.engine.application.port.out.process.ProcessConfigPort;
import io.miranum.platform.engine.application.port.out.schema.JsonSchemaPort;
import io.miranum.platform.engine.domain.jsonschema.JsonSchema;
import io.miranum.platform.engine.domain.process.MiranumProcessInstance;
import io.miranum.platform.engine.domain.process.MiranumProcessInstanceWithData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service to interact with process instances.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MiranumProcessInstanceQueryService implements MiranumProcessInstanceQuery {


    private final ProcessConfigPort processConfigPort;
    private final ServiceInstanceVariablePort serviceInstanceVariablePort;
    private final JsonSchemaPort jsonSchemaPort;
    private final GetHistoricTasksByInstanceIdPort getHistoricTasksByInstanceIdPort;
    private final MiranumProcessInstancePort miranumProcessInstancePort;


    /**
     * Get all assigned  instances
     *
     * @return assigned  instances
     */
    public List<MiranumProcessInstance> getProcessInstanceByUser(final String userId) {
        return this.miranumProcessInstancePort.getAllByUser(userId);
    }

    /**
     * Get detail information of a instance.
     *
     * @param instanceId Id of the  instance
     * @return instance detail
     */
    public MiranumProcessInstanceWithData getServiceInstanceWithDataByUser(final String userId, final String instanceId) {

        if (!miranumProcessInstancePort.hasAccess(userId, instanceId)) {
            throw new RuntimeException("User does not have access to this instance");
        }

        final MiranumProcessInstanceWithData processInstanceWithData = this.miranumProcessInstancePort.getProcessInstanceWithData(instanceId);

        final MiranumProcessInstance processInstanceInfo = this.searchServiceInstanceById(instanceId).orElseThrow();

        val processConfig = this.processConfigPort.getByRef(processInstanceInfo.getDefinitionKey());
        processInstanceWithData.setConfig(processConfig);

        val tasks = this.getHistoricTasksByInstanceIdPort.getHistoricTasksByInstanceId(instanceId);
        processInstanceWithData.setHistoryTasks(tasks);


        if (StringUtils.isNotBlank(processConfig.getInstanceSchemaKey())) {
            final JsonSchema jsonSchema = this.jsonSchemaPort.getByRef(processConfig.getInstanceSchemaKey());
            final Map<String, Object> variables = this.serviceInstanceVariablePort.getVariables(processInstanceInfo.getId());
            final Map<String, Object> data = this.jsonSchemaPort.filterVariables(variables, processConfig.getInstanceSchemaKey());
            processInstanceWithData.setData(data);
            processInstanceWithData.setJsonSchema(jsonSchema.asMap());
        }

        return processInstanceWithData;
    }


    @Override
    public Optional<MiranumProcessInstance> searchServiceInstanceById(final String instanceId) {
        return this.miranumProcessInstancePort.searchProcessInstanceById(instanceId);
    }

}
