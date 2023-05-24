package io.miranum.platform.engine.processconfig.domain.model;

import lombok.*;

/**
 * Dynamic configuration entry.
 *
 * @author externer.dl.horn
 */
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ConfigEntry {

    /**
     * Key of the config entry.
     */
    private String key;

    /**
     * Value of the config.
     */
    private String value;

}
