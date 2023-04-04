package io.miragon.miranum.connect.worker.impl;

import java.util.ArrayList;
import java.util.List;

public class WorkerInfoRegistry {

    private final List<WorkerInfo> workerInfos = new ArrayList<>();

    public void addWorkerInfo(WorkerInfo workerInfo) {
        workerInfos.add(workerInfo);
    }

    public List<WorkerInfo> getWorkerInfos() {
        return workerInfos;
    }
}