package io.miranum.platform.engine.processdefinition.domain.service;

import io.miranum.platform.engine.adapter.in.process.ProcessConstants;
import io.miranum.platform.engine.processdefinition.domain.mapper.ServiceDefinitionMapper;
import io.miranum.platform.engine.processdefinition.domain.model.ServiceDefinition;
import io.miranum.platform.engine.processdefinition.domain.model.ServiceDefinitionDetail;
import io.miranum.platform.engine.processinstance.domain.model.ServiceInstance;
import io.miranum.platform.engine.processinstance.domain.service.ServiceInstanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Service to query service definitions.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceDefinitionService {

    private final RepositoryService repositoryService;
    private final RuntimeService runtimeService;

    private final ServiceDefinitionMapper serviceDefinitionMapper;
    private final ServiceInstanceService serviceInstanceService;

    /**
     * Start a service definition with the given parameters.
     *
     * @param detail    service definition
     * @param variables data with which the process is started
     * @param userId    id of the user that starts the process
     */
    public void startInstance(final ServiceDefinitionDetail detail, final String businessKey, final Map<String, Object> variables, final String userId) {
        log.debug("Start Process: " + detail.getKey());

        //create service instance
        final ServiceInstance serviceInstance = this.serviceInstanceService.creatServiceInstance(detail.getName(), detail.getKey());

        //add other serializer
        variables.put(ProcessConstants.PROCESS_STARTER_OF_INSTANCE, userId);
        variables.put(ProcessConstants.PROCESS_STATUS, "Gestartet");
        variables.put(ProcessConstants.PROCESS_INFO_ID, Variables.stringValue(serviceInstance.getId(), true));


        //start instance
        ProcessInstance processInstance;
        if (StringUtils.isEmpty(businessKey)) {
            processInstance = this.runtimeService.startProcessInstanceByKey(detail.getKey(), variables);
        } else {
            processInstance = this.runtimeService.startProcessInstanceByKey(detail.getKey(), businessKey, variables);
        }

        //Authorization hinzufügen
        this.serviceInstanceService.authorizeServiceInstance(processInstance.getId(), userId);

        //Update processInstanceId
        this.serviceInstanceService.updateInstanceId(serviceInstance.getId(), processInstance.getId());

        log.info("process instance for key {} started: {}", detail.getKey(), processInstance.getId());
    }


    /**
     * Get all service definitions.
     *
     * @return all service definitions
     */
    public List<ServiceDefinition> getServiceDefinitions() {
        final List<ProcessDefinition> serviceDefinitions = this.repositoryService.createProcessDefinitionQuery()
                .latestVersion()
                .list();
        return this.serviceDefinitionMapper.map(serviceDefinitions);
    }

    /**
     * Get a service definition.
     * Does a check if the given authentication can access the service.
     *
     * @param key    key of the service definition
     * @param userId id of the user
     * @param groups groups of the user
     * @return service definition detail
     */
    public ServiceDefinitionDetail getServiceDefinitionDetail(final String key, final String userId, final List<String> groups) {

        final ProcessDefinition processDefinition = this.repositoryService.createProcessDefinitionQuery()
                .latestVersion()
                .processDefinitionKey(key)
                .singleResult();

        return this.serviceDefinitionMapper.map(processDefinition);
    }

}
