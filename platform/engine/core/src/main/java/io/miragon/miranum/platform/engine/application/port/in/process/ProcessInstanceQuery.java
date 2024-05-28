package io.miragon.miranum.platform.engine.application.port.in.process;

import io.miragon.miranum.platform.engine.domain.process.MiranumProcessInstance;

import java.util.List;

/**
 * Service to interact with process instances.
 */
public interface ProcessInstanceQuery {

    List<MiranumProcessInstance> getProcessInstanceByUser(final String userId, String query);

}
