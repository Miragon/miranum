package io.miranum.platform.s3.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileData {
    public static final int LENGTH_PATH_TO_FILE = 1024;
    public static final int MIN_EXPIRES_IN_MINUTES = 1;
    private String pathToFile;

    private Integer expiresInMinutes;

    private LocalDate endOfLife;

}
