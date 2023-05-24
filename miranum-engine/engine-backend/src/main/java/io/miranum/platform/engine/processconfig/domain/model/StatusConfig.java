package io.miranum.platform.engine.processconfig.domain.model;

import lombok.*;

/**
 * Status configuration.
 * Used to display the current status of the process instance.
 *
 * @author externer.dl.horn
 */
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class StatusConfig {

    /**
     * Key of the status config.
     */
    private String key;

    /**
     * Label of the status config.
     * Used to display the current status.
     */
    private String label;

    /**
     * Position of the status config.
     * Used to order them correctly.
     */
    private Integer position;

}
