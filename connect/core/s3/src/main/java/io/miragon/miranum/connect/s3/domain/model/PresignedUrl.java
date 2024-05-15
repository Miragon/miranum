package io.miragon.miranum.connect.s3.domain.model;

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
