package io.miragon.miraum.fitconnect.integration.destinationconnector;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "fitconnect.subscriber")
@Getter
@Setter
public class AuthorityProperties {

    private Map<String, String> processkeyToDestinationMap;
    private long fixedRate = 60_000;
    private String privateKeyDecryptionPath;
    private String privateKeySigningPath;
}