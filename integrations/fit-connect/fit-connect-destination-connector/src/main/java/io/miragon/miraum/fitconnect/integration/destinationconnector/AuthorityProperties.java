package io.miragon.miraum.fitconnect.integration.destinationconnector;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fitconnect.subscriber")
@Getter
@Setter
public class AuthorityProperties {

    private String destinationId;
    private String processKey;
    private long fixedRate = 60_000;
    private String privateKeyDecryptionPath;
    private String privateKeySigningPath;
}