package io.miragon.miranum.platform.example.adapter.out.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.mail")
@Getter
@Setter
public class EmailProperties {

    private String host;
    private Integer port;
    private String protocol;
    private String username;
    private String password;
    private String from;
}
