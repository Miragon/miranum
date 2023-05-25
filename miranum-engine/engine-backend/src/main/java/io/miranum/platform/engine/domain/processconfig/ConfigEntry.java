package io.miranum.platform.engine.domain.processconfig;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Dynamic configuration entry.
 *
 * @author externer.dl.horn
 */
@Getter
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
