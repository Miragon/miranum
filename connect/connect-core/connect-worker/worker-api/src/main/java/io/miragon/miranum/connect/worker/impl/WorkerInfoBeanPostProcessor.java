package io.miragon.miranum.connect.worker.impl;

public class WorkerInfoBeanPostProcessor  {

    public WorkerInfoBeanPostProcessor(WorkerInfoRegistry workerInfoRegistry, WorkerInitializer workerInitializer) {
        workerInitializer.initialize(new InitializeWorkerCommand(workerInfoRegistry.getWorkerInfos()));
    }
}