package io.miranum.platform.engine.application.port.in.process;

import io.miranum.platform.engine.domain.process.MiranumProcessInstance;
import org.springframework.data.domain.Page;

/**
 * Service to interact with process instances.
 */
public interface ProcessInstanceQuery {

    Page<MiranumProcessInstance> getProcessInstanceByUser(final String userId, int page, int size, String query);

}
