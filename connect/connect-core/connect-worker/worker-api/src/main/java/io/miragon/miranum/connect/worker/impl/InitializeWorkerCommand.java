package io.miragon.miranum.connect.worker.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class InitializeWorkerCommand {

    private final List<WorkerInfo> workerList;

}
