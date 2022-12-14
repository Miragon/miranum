package io.miragon.miranum.connect.binder.application.service;

import io.miragon.miranum.connect.binder.application.port.in.InitalizeWorkerCommand;
import io.miragon.miranum.connect.binder.application.port.out.BindWorkerPort;
import io.miragon.miranum.connect.binder.application.service.worker.DefaultWorker;
import io.miragon.miranum.connect.binder.application.service.worker.Input;
import io.miragon.miranum.connect.binder.application.service.worker.Output;
import io.miragon.miranum.connect.binder.domain.WorkerInfo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;

class InitalizeWorkerServiceTest {


    private final BindWorkerPort bindWorkerPort =
            Mockito.mock(BindWorkerPort.class);

    private final InitalizeWorkerService initalizeWorkerService =
            new InitalizeWorkerService(this.bindWorkerPort);

    @Test
    void givenDefaultUseCase_thenBinderIsCalledOnce() throws NoSuchMethodException {
        final List<WorkerInfo> workerInfos = this.givenDefaultWorkerList();

        final InitalizeWorkerCommand command = new InitalizeWorkerCommand(workerInfos);

        this.initalizeWorkerService.initalize(command);

        then(this.bindWorkerPort).should().bind(eq(workerInfos.get(0)));
        then(this.bindWorkerPort).shouldHaveNoMoreInteractions();
    }

    private List<WorkerInfo> givenDefaultWorkerList() throws NoSuchMethodException {
        final DefaultWorker defaultWorker = new DefaultWorker();
        final WorkerInfo workerInfo = new WorkerInfo("test", 30000L, defaultWorker, defaultWorker.getClass().getMethod("doSomething", Input.class), Input.class, Output.class);
        return List.of(workerInfo);
    }

}
