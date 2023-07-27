package io.miranum.platform.engine.application.service;

import io.miranum.platform.engine.adapter.out.persistance.MiranumProcessInstanceMapper;
import io.miranum.platform.engine.adapter.out.persistance.MiranumProcessInstanceRepository;
import io.miranum.platform.engine.application.port.in.process.MiranumProcessInstanceQuery;
import io.miranum.platform.engine.application.port.out.engine.ServiceInstanceVariablePort;
import io.miranum.platform.engine.application.port.out.process.ProcessConfigPort;
import io.miranum.platform.engine.application.port.out.schema.JsonSchemaPort;
import io.miranum.platform.engine.domain.jsonschema.JsonSchema;
import io.miranum.platform.engine.domain.process.MiranumProcessInstance;
import io.miranum.platform.engine.processinstance.domain.mapper.HistoryTaskMapper;
import io.miranum.platform.engine.processinstance.domain.model.ServiceInstanceWithData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.HistoryService;
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

    private final HistoryService historyService;

    private final ProcessConfigPort processConfigPort;

    private final MiranumProcessInstanceRepository processInstanceInfoRepository;

    private final MiranumProcessInstanceMapper serviceInstanceMapper;
    private final HistoryTaskMapper historyTaskMapper;

    private final JsonSchemaPort jsonSchemaPort;
    private final ServiceInstanceVariablePort serviceInstanceVariablePort;


    /**
     * Get all assigned  instances
     *
     * @return assigned  instances
     */
    public List<MiranumProcessInstance> getProcessInstanceByUser(final String userId) {
        final List<String> processAuthIds = this.serviceInstanceAuthService.getAllServiceInstanceIdsByUser(userId);
        return this.serviceInstanceMapper.map2Model(this.processInstanceInfoRepository.findAllByInstanceIdIn(processAuthIds));
    }

    /**
     * Get detail information of a instance.
     *
     * @param infoId Id of the  instance
     * @return instance detail
     */
    public ServiceInstanceWithData getServiceInstanceDetail(final String userId, final String infoId) {

        final MiranumProcessInstance processInstanceInfo = this.getServiceInstanceById(infoId).orElseThrow();

        val processConfig = this.processConfigPort.getByRef(processInstanceInfo.getDefinitionKey());
        val tasks = this.historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceInfo.getId())
                .list();

        final ServiceInstanceWithData detail = this.serviceInstanceMapper.map2Detail(processInstanceInfo);
        detail.setConfig(processConfig);
        detail.setHistoryTasks(this.historyTaskMapper.map2Model(tasks));

        if (StringUtils.isNotBlank(processConfig.getInstanceSchemaKey())) {
            final JsonSchema jsonSchema = this.jsonSchemaPort.getByRef(processConfig.getInstanceSchemaKey());
            final Map<String, Object> variables = this.serviceInstanceVariablePort.getVariables(processInstanceInfo.getId());
            final Map<String, Object> data = this.jsonSchemaPort.filterVariables(variables, processConfig.getInstanceSchemaKey());
            detail.setData(data);
            detail.setJsonSchema(jsonSchema.asMap());
        }

        return detail;
    }


    private Optional<MiranumProcessInstance> getServiceInstanceById(final String id) {
        return this.processInstanceInfoRepository.findById(id)
                .map(this.serviceInstanceMapper::map2Model);
    }

}
