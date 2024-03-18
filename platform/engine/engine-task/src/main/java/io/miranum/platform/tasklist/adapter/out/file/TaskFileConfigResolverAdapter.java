package io.miranum.platform.tasklist.adapter.out.file;

import io.holunda.polyflow.view.Task;
import io.miranum.platform.tasklist.adapter.out.schema.TaskVariables;
import io.miranum.platform.tasklist.application.port.out.file.TaskFileConfigResolverPort;
import io.miranum.platform.tasklist.domain.TaskFileConfig;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.holunda.camunda.bpm.data.CamundaBpmData.reader;

@Component
public class TaskFileConfigResolverAdapter implements TaskFileConfigResolverPort {

    public static final String FILEPATH_DELIMITER = ";";

    @Override
    public TaskFileConfig apply(Task task) {

        final String processFileContext = reader(task.getPayload()).getOrDefault(TaskVariables.PROCESS_FILE_CONTEXT, null);

        final String processAsyncConfig = reader(task.getPayload()).getOrDefault(TaskVariables.PROCESS_ASYNC_CONFIG, null);

        final String processSyncConfig = reader(task.getPayload()).getOrDefault(TaskVariables.PROCESS_SYNC_CONFIG, null);

        final String filePaths = reader(task.getPayload()).getOrDefault(TaskVariables.FILE_PATHS, null);

        final String filePathsReadonly = reader(task.getPayload()).getOrDefault(TaskVariables.FILE_PATHS_READONLY, null);

        List<String> filePathsList = new ArrayList<>();
        List<String> filePathsReadOnlyList = new ArrayList<>();

        if (filePaths != null) {
            filePathsList = Arrays.stream(filePaths.split(FILEPATH_DELIMITER)).collect(Collectors.toList());
        }

        if (filePathsReadonly != null) {
           filePathsReadOnlyList = Arrays.stream(filePathsReadonly.split(FILEPATH_DELIMITER)).collect(Collectors.toList());
        }

        if (processFileContext == null) {
            throw  new NoFileContextException("No file context found for task");
        }

        return new TaskFileConfig(processFileContext, processAsyncConfig, processSyncConfig,filePathsList,filePathsReadOnlyList);
    }
}
