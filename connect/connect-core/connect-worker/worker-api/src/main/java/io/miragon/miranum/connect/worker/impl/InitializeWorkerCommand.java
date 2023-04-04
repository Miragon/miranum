package io.miragon.miranum.connect.worker.impl;

import java.util.List;

public record InitializeWorkerCommand(List<WorkerInfo> workerList) {
}