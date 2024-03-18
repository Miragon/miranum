package io.miranum.platform.tasklist.adapter.in.rest.impl;

import io.miranum.platform.tasklist.adapter.in.rest.api.FileApiDelegate;
import io.miranum.platform.tasklist.application.port.in.WorkOnTaskFile;
import io.miranum.platform.tasklist.domain.PresignedUrlAction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

/**
 * File API delegate for work on one user task files.
 */
@Component
@RequiredArgsConstructor
public class FileApiDelegateImpl implements FileApiDelegate {

    private final WorkOnTaskFile workOnTaskFile;

    @Override
    public ResponseEntity<List<String>> getFileNames(String taskId, String filePath) {
        return ok(workOnTaskFile.getFileNames(taskId,filePath));
    }

    @Override
    public ResponseEntity<String> getPresignedUrlForFile(String taskId, String fileName, String filePath, String requestMethod){
        return ok(workOnTaskFile.getPresignedUrl(PresignedUrlAction.valueOf(requestMethod), taskId, filePath, fileName));
    }


}
