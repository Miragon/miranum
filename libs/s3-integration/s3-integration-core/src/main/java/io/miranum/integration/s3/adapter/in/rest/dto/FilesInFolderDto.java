package io.miranum.integration.s3.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
@Schema(description = "File paths")
public class FilesInFolderDto {

    private Set<String> pathToFiles;

}
