package io.miranum.platform.engine.adapter.out.engine;

import io.miranum.platform.engine.application.port.out.history.GetHistoricTasksByInstanceIdPort;
import io.miranum.platform.engine.domain.process.HistoryTask;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetHistoricTasksByInstanceIdImpl implements GetHistoricTasksByInstanceIdPort {
    private final HistoryService historyService;
    private final HistoryTaskMapper historyTaskMapper;

    @Override
    public List<HistoryTask> getHistoricTasksByInstanceId(String instanceId) {
        final List<HistoricTaskInstance> tasks = this.historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(instanceId)
                .list();

        return historyTaskMapper.map2Model(tasks);
    }
}
