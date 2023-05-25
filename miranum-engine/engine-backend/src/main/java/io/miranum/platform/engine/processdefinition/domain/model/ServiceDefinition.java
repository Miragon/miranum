package io.miranum.platform.engine.processdefinition.domain.model;

import lombok.*;

/**
 * Representation of a service definition.
 *
 * @author externer.dl.horn
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ServiceDefinition {

    /**
     * Key of the process definition.
     */
    private final String key;

    /**
     * Name of the process definition.
     */
    private final String name;

    /**
     * Description provides further information about the process definition.
     */
    private final String description;

    /**
     * Versiontag of the process definition.
     */
    private final String versionTag;

}
