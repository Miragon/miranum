package io.miranum.platform.engine.application.port.in.process;

import io.miranum.platform.engine.domain.file.PresignedUrlAction;

import java.util.List;

public interface GetPresignedUrlForProcessStartUseCase {

    String getPresignedUrl(final PresignedUrlAction action, final String definitionKey, final String filePath, final String fileName, final String userId, final List<String> groups);

}
