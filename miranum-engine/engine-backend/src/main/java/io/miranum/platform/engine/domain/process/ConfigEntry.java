package io.miranum.platform.engine.domain.process;

import lombok.*;

/**
 * Dynamic configuration entry.
 *
 * @author externer.dl.horn
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Setter
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
