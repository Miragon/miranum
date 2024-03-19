package io.miranum.platform.engine.adapter.in.rest.process;

import io.miranum.platform.engine.domain.process.HistoryTask;
import io.miranum.platform.engine.domain.process.ProcessConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Transport object of the {@link }
 *
 * @author externer.dl.horn
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessInstanceDetailDto {

    /**
     * Id of the process instance.
     */
    private String id;

    /**
     * Name of the corresponding process defintion.
     */
    private String definitionName;

    /**
     * Start time
     */
    private Date startTime;

    /**
     * End time
     */
    private Date endTime;

    /**
     * Status
     */
    private String status;

    /**
     * Status key
     */
    private String statusKey;

    /**
     * Description
     */
    private String description;

    /**
     * Config of the corresponding process definition.
     */
    private ProcessConfig processConfig;

    /**
     * Tasks that have been completed so far.
     */
    private List<HistoryTask> historyTasks;


    /**
     * Provided data of the instance.
     */
    private Map<String, Object> data;

    /**
     * json schema
     */
    private Map<String, Object> jsonSchema;

}
