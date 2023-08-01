package io.miranum.integration.s3.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Presigned file url")
public class PresignedUrlDto {

    private String url;
    private String path;
    private String action;

}
