package io.miranum.platform.engine.application.port.in.process;

public interface UpdateProcessInstanceDescriptionUseCase {

    void updateDescription(String processInstanceId, String description);

}
