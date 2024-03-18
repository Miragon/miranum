package de.muenchen.oss.digiwf.s3.integration.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PresignedUrl {

    private String url;
    private String path;
    private String action;

}
