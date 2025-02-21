package io.miragon.miranum.platform.engine.application.port.out.process;

import io.miragon.miranum.platform.engine.domain.process.MiranumProcessInstance;
import io.miragon.miranum.platform.engine.domain.process.MiranumProcessInstanceWithData;

import java.util.List;
import java.util.Optional;

public interface ProcessInstanceOutPort {

    void save(MiranumProcessInstance processInstance);

    List<MiranumProcessInstance> getAllByUser(String userId);

    void authorizeServiceInstance(final String instanceId, final String userId);

    MiranumProcessInstanceWithData getProcessInstanceWithData(final String instanceId);

    boolean hasAccess(String instanceId, String userId);

    Optional<MiranumProcessInstance> searchProcessInstanceById(String instanceId);

}
