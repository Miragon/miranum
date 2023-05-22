package io.miranum.platform.engine.processinstance.domain.mapper;

import io.miranum.platform.engine.processinstance.domain.model.HistoryTask;
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
