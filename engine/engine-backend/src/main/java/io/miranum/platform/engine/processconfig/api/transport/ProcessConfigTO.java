package io.miranum.platform.engine.processconfig.api.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * Transport object of the ProcessConfig
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessConfigTO {

    /**
     * key of the process config.
     */
    @NotBlank
    private String key;

    /**
     * default status dokument.
     */
    private String statusDokument;

    /**
     * status config of the process definition.
     */
    @Builder.Default
    private List<StatusConfigTO> statusConfig = new ArrayList<>();

    /**
     * dynamic config entries.
     */
    @Builder.Default
    private List<ConfigEntryTO> configs = new ArrayList<>();
}
