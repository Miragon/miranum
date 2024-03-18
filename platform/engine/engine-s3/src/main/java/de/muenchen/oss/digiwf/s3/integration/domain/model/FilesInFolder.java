package de.muenchen.oss.digiwf.s3.integration.domain.model;

import lombok.Data;

import java.util.Set;

@Data
public class FilesInFolder {

    public static final String SEPARATOR = "/";


    private Set<String> pathToFiles;

}
