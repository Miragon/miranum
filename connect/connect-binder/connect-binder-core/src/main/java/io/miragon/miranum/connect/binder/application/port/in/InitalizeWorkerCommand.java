package io.miragon.miranum.connect.binder.application.port.in;

import io.miragon.miranum.connect.binder.domain.WorkerInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class InitalizeWorkerCommand {

    private final List<WorkerInfo> workerList;

}
