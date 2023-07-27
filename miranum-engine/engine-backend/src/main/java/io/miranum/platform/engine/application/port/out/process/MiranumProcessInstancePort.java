package io.miranum.platform.engine.application.port.out.process;

import io.miranum.platform.engine.domain.process.MiranumProcessInstance;

import java.util.List;

public interface MiranumProcessInstancePort {

    void save(MiranumProcessInstance processInstance);

    List<MiranumProcessInstance> getAllByUser(String userId);

    void authorizeServiceInstance(final String instanceId, final String userId);

}
