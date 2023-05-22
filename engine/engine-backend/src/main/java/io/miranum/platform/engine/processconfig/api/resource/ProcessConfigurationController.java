package io.miranum.platform.engine.processconfig.api.resource;

import io.miranum.platform.engine.processconfig.api.mapper.ProcessConfigApiMapper;
import io.miranum.platform.engine.processconfig.api.transport.ProcessConfigTO;
import io.miranum.platform.engine.processconfig.domain.model.ProcessConfig;
import io.miranum.platform.engine.processconfig.domain.service.ProcessConfigService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Validated
@RestController
@Transactional
@RequestMapping("/rest/processconfig")
@RequiredArgsConstructor
@Tag(name = "ProcessConfigurationController", description = "API to interact with the process configuration")
public class ProcessConfigurationController {

    private final ProcessConfigService processConfigService;
    private final ProcessConfigApiMapper processConfigApiMapper;

    /**
     * Create a new Config.
     *
     * @param to process config that should be created
     * @return process config
     */
    @PostMapping
    public ResponseEntity<ProcessConfigTO> createConfig(@RequestBody @Valid final ProcessConfigTO to) {
        val processConfig = this.processConfigService.saveProcessConfig(this.processConfigApiMapper.map(to));
        return ResponseEntity.ok(this.processConfigApiMapper.map2TO(processConfig));
    }

    /**
     * Get a process config by key
     *
     * @param configKey key of the process config
     * @return process config
     */
    @GetMapping("/{key}")
    public ResponseEntity<ProcessConfigTO> getConfig(@PathVariable("key") @NotBlank final String configKey) {
        final ProcessConfig processConfig = this.processConfigService.getProcessConfig(configKey)
                .orElseThrow(() -> new RuntimeException("Config not available"));
        return ResponseEntity.ok(this.processConfigApiMapper.map2TO(processConfig));
    }

}
