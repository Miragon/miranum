package io.miragon.miraum.fitconnect.integration.authority;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "fitconnect.subscriber")
@Getter
@Setter
public class AuthorityProperties {

    private String destinationId;
    private String privateKeyDecryptionPath;
    private String privateKeySigningPath;
}