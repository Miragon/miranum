package io.muenchendigital.digiwf.s3.integration;

import io.muenchendigital.digiwf.s3.integration.application.port.in.CreatePresignedUrlsInPort;
import io.muenchendigital.digiwf.spring.security.authentication.UserAuthenticationProvider;
import io.muenchendigital.digiwf.spring.security.autoconfiguration.SpringSecurityAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
    classes = S3IntegrationApplication.class
)
@ActiveProfiles({"itest", "local"})
@EnableAutoConfiguration(
    exclude = {
        SpringSecurityAutoConfiguration.class,
        OAuth2ClientAutoConfiguration.class,
        OAuth2ResourceServerAutoConfiguration.class
    }
)
public class ServiceIntegrationTest {

  @MockBean
  private UserAuthenticationProvider provider;

  @Autowired(required = false)
  private CreatePresignedUrlsInPort port;

  @Test
  void starts_service() {
    assertThat(port).isNotNull();
  }

}
