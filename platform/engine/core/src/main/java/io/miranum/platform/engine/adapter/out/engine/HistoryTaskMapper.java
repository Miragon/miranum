package io.miranum.platform.engine.adapter.out.engine;

import io.miranum.platform.engine.domain.process.HistoryTask;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Map between {@link HistoryTask} and {@link HistoricTaskInstance}
 */
@Mapper
public interface HistoryTaskMapper {

    List<HistoryTask> map2Model(List<HistoricTaskInstance> list);

}
