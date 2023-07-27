package io.miranum.platform.engine.application.port.in.process;

import io.miranum.platform.engine.domain.process.MiranumProcessInstance;
import io.miranum.platform.engine.processinstance.domain.model.ServiceInstanceWithData;

import java.util.List;
import java.util.Optional;

/**
 * Service to interact with process instances.
 */
public interface MiranumProcessInstanceQuery {

    List<MiranumProcessInstance> getProcessInstanceByUser(final String userId);

    ServiceInstanceWithData getServiceInstanceDetail(final String userId, final String instanceId);

    Optional<MiranumProcessInstance> searchServiceInstanceById(final String id);

}
