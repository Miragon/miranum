package io.miranum.platform.engine.adapter.out.file;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Component
@Validated
@ConfigurationProperties(prefix = "miranum.s3service")
public class S3ProcessProperties {

    @NotBlank
    private String httpAPI;
}
