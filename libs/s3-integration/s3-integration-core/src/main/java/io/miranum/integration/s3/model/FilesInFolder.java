package io.miranum.integration.s3.model;

import lombok.Data;

import java.util.Set;

@Data
public class FilesInFolder {

    private Set<String> pathToFiles;

}
