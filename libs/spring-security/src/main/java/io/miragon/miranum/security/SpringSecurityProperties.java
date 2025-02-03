package io.miragon.miranum.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "miranum.security")
public class SpringSecurityProperties {

    private String clientRegistration;
    private List<String> permittedUrls = new ArrayList<>();
    private Server server = new Server();
    private Client client = new Client();

    @Data
    public static class Server {
        private String baseUrl;
        private String realm;
        private String userNameAttribute;
    }

    @Data
    public static class Client {
        private String enabled;
        private String clientId;
        private String clientSecret;
    }

}
