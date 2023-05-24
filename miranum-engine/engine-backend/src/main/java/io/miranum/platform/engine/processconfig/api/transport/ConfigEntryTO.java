package io.miranum.platform.engine.processconfig.api.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Transport object of the ConfigEntry
 *
 * @author externer.dl.horn
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfigEntryTO {

    /**
     * Key of the config entry.
     */
    @NotBlank
    private String key;

    /**
     * Value of the config.
     */
    @NotBlank
    private String value;

}
