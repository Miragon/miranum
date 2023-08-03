package io.miranum.platform.engine.application.port.in.process;

import java.util.List;

public interface GetFileNamesForProcessStartUseCase {

    List<String> getFileNames(final String definitionKey, final String filePath, final String userId, final List<String> groups);

}
