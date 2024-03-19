package io.miranum.platform.engine.adapter.in.rest.process;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Transport object of the ServiceInstance
 *
 * @author externer.dl.horn
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessInstanceDto {

    /**
     * Id of the process instance.
     */
    private String id;

    /**
     * Name of the corresponding process definition.
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
     * description
     */
    private String description;

}
