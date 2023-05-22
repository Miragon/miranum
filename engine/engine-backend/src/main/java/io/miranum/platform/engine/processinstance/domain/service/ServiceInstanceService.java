package io.miranum.platform.engine.processinstance.domain.service;

import io.miranum.platform.engine.jsonschema.domain.model.JsonSchema;
import io.miranum.platform.engine.jsonschema.domain.service.JsonSchemaService;
import io.miranum.platform.engine.processconfig.domain.service.ProcessConfigService;
import io.miranum.platform.engine.processinstance.domain.mapper.HistoryTaskMapper;
import io.miranum.platform.engine.processinstance.domain.mapper.ServiceInstanceMapper;
import io.miranum.platform.engine.processinstance.domain.model.ServiceInstance;
import io.miranum.platform.engine.processinstance.domain.model.ServiceInstanceDetail;
import io.miranum.platform.engine.processinstance.infrastructure.entity.ServiceInstanceEntity;
import io.miranum.platform.engine.processinstance.infrastructure.repository.ProcessInstanceInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.HistoryService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service to interact with process instances.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceInstanceService {

    private final HistoryService historyService;

    private final ProcessConfigService processConfigService;
    private final ServiceInstanceAuthService serviceInstanceAuthService;

    private final ProcessInstanceInfoRepository processInstanceInfoRepository;

    private final ServiceInstanceMapper serviceInstanceMapper;
    private final HistoryTaskMapper historyTaskMapper;

    private final JsonSchemaService jsonSchemaService;
    private final ServiceInstanceDataService serviceInstanceDataService;


    /**
     * Get all assigned  instances
     *
     * @return assigned  instances
     */
    public List<ServiceInstance> getProcessInstanceByUser(final String userId) {
        final List<String> processAuthIds = this.serviceInstanceAuthService.getAllServiceInstanceIdsByUser(userId);
        return this.serviceInstanceMapper.map2Model(this.processInstanceInfoRepository.findAllByInstanceIdIn(processAuthIds));
    }

    /**
     * Get detail information of a instance.
     *
     * @param infoId Id of the  instance
     * @return instance detail
     */
    public ServiceInstanceDetail getServiceInstanceDetail(final String infoId) {

        final ServiceInstance processInstanceInfo = this.getServiceInstanceById(infoId).orElseThrow();

        val processConfig = this.processConfigService.getProcessConfig(processInstanceInfo.getDefinitionKey()).orElseThrow();
        val tasks = this.historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceInfo.getInstanceId())
                .list();

        final ServiceInstanceDetail detail = this.serviceInstanceMapper.map2Detail(processInstanceInfo);
        detail.setConfig(processConfig);
        detail.setHistoryTasks(this.historyTaskMapper.map2Model(tasks));

        if (StringUtils.isNotBlank(processConfig.getInstanceSchemaKey())) {
            final JsonSchema jsonSchema = this.jsonSchemaService.getByKey(processConfig.getInstanceSchemaKey()).orElseThrow();
            final Map<String, Object> data = this.serviceInstanceDataService.getVariables(processInstanceInfo.getInstanceId(), jsonSchema);

            detail.setData(data);
            detail.setJsonSchema(jsonSchema.getSchemaMap());
        }

        return detail;
    }

    /**
     * Get service instance by  id.
     *
     * @param id Id of the service instance
     * @return service instance
     */
    public Optional<ServiceInstance> getServiceInstanceById(final String id) {
        return this.processInstanceInfoRepository.findById(id)
                .map(this.serviceInstanceMapper::map2Model);
    }


    /**
     * Get service instance by instance id.
     *
     * @param instanceId Id of the instance
     * @return service instance
     */
    public Optional<ServiceInstance> getServiceInstanceByInstanceId(final String instanceId) {
        return this.processInstanceInfoRepository.findByInstanceId(instanceId)
                .map(this.serviceInstanceMapper::map2Model);
    }

    /**
     * Create a Service Instance object.
     *
     * @param definitionName name of the definition
     * @param definitionKey  key of the definition
     * @return created ServiceInstance
     */
    public ServiceInstance creatServiceInstance(final String definitionName, final String definitionKey) {
        final ServiceInstance serviceInstance = ServiceInstance.builder()
                .definitionName(definitionName)
                .startTime(new Date())
                .definitionKey(definitionKey)
                .status("Gestartet")
                .build();
        return this.saveServiceInstance(serviceInstance);
    }

    /**
     * Save an extisting service instance
     *
     * @param serviceInstance Instance that is saved
     * @return saved service instance
     */
    public ServiceInstance saveServiceInstance(final ServiceInstance serviceInstance) {
        final ServiceInstanceEntity persistedProcessInstanceInfo = this.processInstanceInfoRepository.save(this.serviceInstanceMapper.map2Entity(serviceInstance));
        return this.serviceInstanceMapper.map2Model(persistedProcessInstanceInfo);
    }

    /**
     * Create
     *
     * @param instanceId Id of the corresponding
     * @param userId     Id of the user
     */
    public void authorizeServiceInstance(final String instanceId, final String userId) {
        this.serviceInstanceAuthService.createAuthorization(instanceId, userId);
    }

    /**
     * Update the instance Id of a service instance
     *
     * @param serviceInstanceId Id of the service instance
     * @param instanceId        Id of the corresponding process instance
     */
    public void updateInstanceId(final String serviceInstanceId, final String instanceId) {
        final ServiceInstance serviceInstance = this.getServiceInstanceById(serviceInstanceId).orElseThrow();
        serviceInstance.updateProcessInstanceId(instanceId);
        this.saveServiceInstance(serviceInstance);
    }

    /**
     * Get all instances expired at a reference date.
     *
     * @param referenceDate the reference date for expiration
     * @return expired instances
     */
    public List<ServiceInstance> getProcessInstanceByRemovalTimeBefore(final Date referenceDate) {
        final List<ServiceInstanceEntity> instances = this.processInstanceInfoRepository.findByRemovalTimeBefore(referenceDate);
        return this.serviceInstanceMapper.map2Model(instances);
    }

    /**
     * Cleanup instance with given id.
     *
     * @param instanceId the id
     */
    public void cleanupInstance(final String instanceId) {
        final Optional<ServiceInstanceEntity> entity = this.processInstanceInfoRepository.findByInstanceId(instanceId);
        if (entity.isPresent()) {
            this.processInstanceInfoRepository.delete(entity.get());
            log.info("Service instance cleaned up: {}", entity.get().getInstanceId());
        }
    }

}
