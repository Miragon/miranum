package io.miragon.miraum.fitconnect.integration.destinationconnector;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "fitconnect.subscriber")
@Getter
@Setter
@Valid
public class SubscriberProperties {

    @NotNull
    private Map<String, String> processDestinationMap;
    private long fixedRate = 60_000;
    @NotNull
    private String privateKeyDecryptionPath;
    @NotNull
    private String privateKeySigningPath;
}
