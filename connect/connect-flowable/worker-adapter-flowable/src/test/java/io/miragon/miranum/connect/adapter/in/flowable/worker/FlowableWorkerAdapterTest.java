package io.miragon.miranum.connect.adapter.in.flowable.worker;

import io.miragon.miranum.connect.worker.impl.WorkerExecutor;
import org.flowable.engine.delegate.JavaDelegate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class FlowableWorkerAdapterTest {

    private JavaDelegateRegistrator javaDelegateRegistrator;
    private JavaDelegateFactory javaDelegateFactory;
    private FlowableWorkerAdapter adapter;

    @BeforeEach
    public void setUp() {
        javaDelegateRegistrator = mock(JavaDelegateRegistrator.class);
        javaDelegateFactory = mock(JavaDelegateFactory.class);
        adapter = new FlowableWorkerAdapter(javaDelegateRegistrator, javaDelegateFactory);
    }

    @Test
    public void testSubscribe() {
        WorkerExecutor executor = mock(WorkerExecutor.class);
        JavaDelegate delegate = mock(JavaDelegate.class);

        when(javaDelegateFactory.createDelegate(executor)).thenReturn(delegate);
        when(executor.getType()).thenReturn("testType");

        adapter.subscribe(executor);

        verify(javaDelegateFactory, times(1)).createDelegate(executor);
        verify(javaDelegateRegistrator, times(1)).register(delegate, "testType");
    }
}
