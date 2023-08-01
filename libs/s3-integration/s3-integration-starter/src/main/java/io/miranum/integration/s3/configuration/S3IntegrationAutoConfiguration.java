package io.miranum.integration.s3.configuration;

import io.minio.MinioClient;
import io.miranum.integration.s3.adapter.out.s3.S3Repository;
import io.miranum.integration.s3.application.CreatePresignedUrlsUseCase;
import io.miranum.integration.s3.application.FileOperationsUseCase;
import io.miranum.integration.s3.application.port.in.CreatePresignedUrlsInPort;
import io.miranum.integration.s3.application.port.in.FileSystemAccessException;
import io.miranum.integration.s3.properties.S3IntegrationProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = "io.miranum.integration.s3")
@EntityScan(basePackages = "io.miranum.integration.s3")
@ComponentScan(basePackages = "io.miranum.integration.s3")
@EnableConfigurationProperties(S3IntegrationProperties.class)
public class S3IntegrationAutoConfiguration {

    public final S3IntegrationProperties s3IntegrationProperties;

    @Bean
    public S3Repository s3Repository() throws FileSystemAccessException {
        final MinioClient minioClient = MinioClient.builder()
                .endpoint(this.s3IntegrationProperties.getUrl())
                .credentials(this.s3IntegrationProperties.getAccessKey(), this.s3IntegrationProperties.getSecretKey())
                .build();
        return new S3Repository(
                this.s3IntegrationProperties.getBucketName(),
                this.s3IntegrationProperties.getUrl(),
                minioClient,
                BooleanUtils.isNotFalse(this.s3IntegrationProperties.getInitialConnectionTest()),
                this.s3IntegrationProperties.getProxyEnabled() ? Optional.of(this.s3IntegrationProperties.getProxyUrl()) : Optional.empty()
        );
    }

    @Bean
    public CreatePresignedUrlsInPort createPresignedUrlsInPort(FileOperationsUseCase fileHandlingService) {
        return new CreatePresignedUrlsUseCase(
                fileHandlingService,
                this.s3IntegrationProperties.getPresignedUrlExpiresInMinutes()
        );
    }

}
