package io.miranum.platform.engine.application.port.in.process;

import io.miranum.platform.engine.domain.process.MiranumProcessInstance;
import io.miranum.platform.engine.domain.process.MiranumProcessInstanceWithData;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * Service to interact with process instances.
 */
public interface MiranumProcessInstanceQuery {

    Page<MiranumProcessInstance> getProcessInstanceByUser(final String userId, int page, int size, String query);

    MiranumProcessInstanceWithData getServiceInstanceWithDataByUser(final String userId, final String instanceId);

    Optional<MiranumProcessInstance> searchServiceInstanceById(final String id);

}
