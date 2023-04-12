package io.miragon.miranum.connect.worker.impl;

import io.miragon.miranum.connect.worker.api.BusinessException;
import io.miragon.miranum.connect.worker.api.TechnicalException;
import io.miragon.miranum.connect.worker.api.WorkerExecuteApi;
import io.miragon.miranum.connect.worker.api.WorkerInterceptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class WorkerExecuteApiImplTest {

    private final WorkerInterceptor interceptor = Mockito.spy(Mockito.mock(WorkerInterceptor.class));
    private final WorkerExecutor workerExecutor = Mockito.mock(WorkerExecutor.class);
    private final WorkerExecuteApi workerExecuteApi = new WorkerExecuteApiImpl(List.of());

    // test data
    private final Map<String, Object> event = Map.of("test", "test");

    @Test
    void testExecuteWorker() throws InvocationTargetException, IllegalAccessException {
        when(this.workerExecutor.getType()).thenReturn("exampleWorker");
        doReturn(Map.class).when(this.workerExecutor).getInputType();
        doReturn(Map.class).when(this.workerExecutor).getOutputType();
        when(this.workerExecutor.execute(this.event)).thenReturn(this.event);

        final Object result = this.workerExecuteApi.execute(this.workerExecutor, this.event);
        Assertions.assertEquals(this.event, result);

        // check that the worker was called
        final ArgumentCaptor<Map<String, Object>> inputArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        Mockito.verify(this.workerExecutor).execute(inputArgumentCaptor.capture());
        Assertions.assertEquals(this.event, inputArgumentCaptor.getValue());
    }

    @Test
    void testExecuteWorkerWithInterceptor() throws InvocationTargetException, IllegalAccessException {
        final WorkerExecuteApiImpl workerExecuteApi = new WorkerExecuteApiImpl(List.of(this.interceptor));

        when(this.workerExecutor.getType()).thenReturn("exampleWorker");
        doReturn(Map.class).when(this.workerExecutor).getInputType();
        doReturn(Map.class).when(this.workerExecutor).getOutputType();
        when(this.workerExecutor.execute(this.event)).thenReturn(this.event);

        final Object result = workerExecuteApi.execute(this.workerExecutor, this.event);
        Assertions.assertEquals(this.event, result);

        // check that the interceptor was called
        final ArgumentCaptor<WorkerExecutor> workerExecutorArgumentCaptor = ArgumentCaptor.forClass(WorkerExecutor.class);
        final ArgumentCaptor<Map<String, Object>> interceptorArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        Mockito.verify(this.interceptor).intercept(interceptorArgumentCaptor.capture(), workerExecutorArgumentCaptor.capture());
        Assertions.assertEquals(this.event, interceptorArgumentCaptor.getValue());
    }

    @Test
    void testExecuteWorkerThrowsTechnicalError() throws InvocationTargetException, IllegalAccessException {
        when(this.workerExecutor.getType()).thenReturn("exampleWorker");
        doReturn(Map.class).when(this.workerExecutor).getInputType();
        doReturn(Map.class).when(this.workerExecutor).getOutputType();
        when(this.workerExecutor.execute(this.event)).thenThrow(new TechnicalException("error msg"));

        Assertions.assertThrows(TechnicalException.class, () ->
                this.workerExecuteApi.execute(this.workerExecutor, this.event));
    }

    @Test
    void testExecuteWorkerThrowsBusinessException() throws InvocationTargetException, IllegalAccessException {
        when(this.workerExecutor.getType()).thenReturn("exampleWorker");
        doReturn(Map.class).when(this.workerExecutor).getInputType();
        doReturn(Map.class).when(this.workerExecutor).getOutputType();
        when(this.workerExecutor.execute(this.event)).thenThrow(new BusinessException("400", "error msg"));

        Assertions.assertThrows(BusinessException.class, () ->
                this.workerExecuteApi.execute(this.workerExecutor, this.event));
    }

}
