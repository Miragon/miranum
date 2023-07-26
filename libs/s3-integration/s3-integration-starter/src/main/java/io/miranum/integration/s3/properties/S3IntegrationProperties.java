package io.miranum.integration.s3.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "io.miranum.integration.s3")
public class S3IntegrationProperties {

  @NotBlank
  private String url;

  @NotBlank
  private String accessKey;

  @NotBlank
  private String secretKey;

  @NotBlank
  private String bucketName;

  private Boolean initialConnectionTest;

  private int presignedUrlExpiresInMinutes = 10080; // 7 days

  private Boolean proxyEnabled = false;

  private String proxyUrl;

}
