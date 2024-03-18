package io.miranum.platform.tasklist.domain;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TaskFileConfig {

    public final String processFileContext;

    public final List<String> filePaths;

    public final List<String> filePathsReadonly;

    private static final String ERRTEXT_ILLEGAL_ACCESS = "No access to defined property";


    public void checkReadAccess(final String filePath) {
        try {
            this.checkWriteAccess(filePath);
        } catch (final IllegalResourceAccessException ex) {
            if (filePathsReadonly.isEmpty()) {
                throw new IllegalResourceAccessException(ERRTEXT_ILLEGAL_ACCESS);
            }

            this.filePathsReadonly.stream()
                    .filter(filePath::startsWith)
                    .findFirst()
                    .orElseThrow(() -> new IllegalResourceAccessException(ERRTEXT_ILLEGAL_ACCESS));
        }
    }

    public void checkWriteAccess(final String filePath) {

        if (filePaths.isEmpty()) {
            throw new IllegalResourceAccessException(ERRTEXT_ILLEGAL_ACCESS);
        }

        this.filePaths.stream()
                .filter(filePath::startsWith)
                .findFirst()
                .orElseThrow(() -> new IllegalResourceAccessException(ERRTEXT_ILLEGAL_ACCESS));
    }

}
