package io.miranum.platform.engine.application.port.in.process;

public interface UpdateProcessInstanceStatusUseCase {

    void updateStatus(String processInstanceId, String statusKey, String statusName);

}
