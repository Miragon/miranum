package io.miranum.integration.s3.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "io.miranum.integration.s3.client")
public class S3IntegrationClientProperties {

  private String documentStorageUrl;
  private boolean enableSecurity;

}
