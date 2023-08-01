package io.miranum.platform.engine.application.port.out.history;

import io.miranum.platform.engine.domain.process.HistoryTask;

import java.util.List;

public interface GetHistoricTasksByInstanceIdPort {
    List<HistoryTask> getHistoricTasksByInstanceId(String instanceId);
}
