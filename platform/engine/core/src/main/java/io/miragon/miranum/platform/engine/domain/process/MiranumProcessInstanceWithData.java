package io.miragon.miranum.platform.engine.domain.process;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class MiranumProcessInstanceWithData {

    /**
     * Id of the service instance info object.
     */
    private final String id;
    /**
     * Name of the corresponding process definition.
     */
    private final String definitionName;
    /**
     * Start time
     */
    private final Date startTime;
    /**
     * End time
     */
    private final Date endTime;
    /**
     * Status
     */
    private final String status;
    /**
     * Description provides further information
     */
    private final String description;
    /**
     * Key of the status
     */
    private final String statusKey;
    /**
     * Id of the process instance.
     */
    private String instanceId;
    /**
     * Tasks that have been completed so far.
     */
    private List<HistoryTask> historyTasks;

    /**
     * Provided data of the instance.
     */
    private Map<String, Object> data;


    public void setHistoryTasks(final List<HistoryTask> historyTasks) {
        this.historyTasks = historyTasks;
    }

    public void setData(final Map<String, Object> data) {
        this.data = data;
    }

}
