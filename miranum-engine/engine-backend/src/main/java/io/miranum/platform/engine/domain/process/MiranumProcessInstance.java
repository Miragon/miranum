package io.miranum.platform.engine.domain.process;

import lombok.*;

import java.time.Instant;
import java.util.Date;

/**
 * Representation of a process instance.
 *
 * @author externer.dl.horn
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class MiranumProcessInstance {

    /**
     * Id of the service instance info object.
     */
    private final String id;
    /**
     * Name of the corresponding service definition.
     */
    private final String definitionName;
    /**
     * Key of the corresponding service definition.
     */
    private final String definitionKey;
    /**
     * Start time
     */
    private final Instant startTime;
    /**
     * End time
     */
    private Date endTime;
    /**
     * Removal time
     */
    private Date removalTime;
    /**
     * Status
     */
    private String status;
    /**
     * Status
     */
    private String statusKey;
    /**
     * description
     */
    private String description;

    public MiranumProcessInstance(String definitionKey, String definitionName, String Id) {
        this.id = Id;
        this.definitionKey = definitionKey;
        this.definitionName = definitionName;
        this.startTime = Instant.now();
    }

    public void updateStatus(final String statusKey, final String status) {
        this.status = status;
        this.statusKey = statusKey;
    }

    public void updateDescription(final String description) {
        this.description = description;
    }

    public void updateRemovaltime(final Date removaltime) {
        this.removalTime = removaltime;
    }

    public void finished(Date endTime, Date removalTime) {
        this.endTime = endTime;
        this.removalTime = removalTime;
    }

}
