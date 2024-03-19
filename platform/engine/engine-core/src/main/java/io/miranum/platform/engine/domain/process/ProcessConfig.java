package io.miranum.platform.engine.domain.process;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration for a specific process definition.
 *
 * @author externer.dl.horn
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ProcessConfig {


    public static final String INSTANCE_FILE_PATHS_READONLY = "app_instance_file_paths_readonly";
    public static final String INSTANCE_FILE_PATHS = "app_instance_file_paths";
    public static final String FILE_PATHS = "app_file_paths_readonly";
    public static final String FILE_PATHS_READONLY = "app_file_paths";
    public static final String INSTANCE_SCHEMA_KEY = "app_instance_schema_key";

    /**
     * status config of the process definition.
     */
    private List<StatusConfig> statusConfig = new ArrayList<>();

    /**
     * dynamic config entries.
     */
    private List<ConfigEntry> configs = new ArrayList<>();

    public String getStatus(final String statusKey) {
        return this.statusConfig.stream()
                .filter(config -> statusKey.equals(config.getKey()))
                .map(StatusConfig::getLabel)
                .findAny()
                .orElse(statusKey);
    }

    public String getConfig(final String configKey) {
        return this.configs.stream()
                .filter(obj -> configKey.equals(obj.getKey()))
                .map(ConfigEntry::getValue)
                .findFirst()
                .orElse(null);
    }


}
