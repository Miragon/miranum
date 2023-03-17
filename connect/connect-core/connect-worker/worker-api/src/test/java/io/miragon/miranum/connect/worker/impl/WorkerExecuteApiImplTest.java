package io.miragon.miranum.connect.worker.impl;

import io.miragon.miranum.connect.worker.api.BusinessException;
import io.miragon.miranum.connect.worker.api.TechnicalException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkerExecuteApiImplTest {

    private final WorkerExecuteApiImpl workerExecuteApi = new WorkerExecuteApiImpl(List.of());

    // test data
    private final Map<String, Object> event = Map.of("test", "test");

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        this.workerExecuteApi.register(this.getExampleWorker());
        this.workerExecuteApi.register(this.getExampleWorkerWithoutReturn());
        this.workerExecuteApi.register(this.getTechnicalExceptionIntegraton());
        this.workerExecuteApi.register(this.getBusinessExceptionIntegraton());
    }

    @Test
    void testExecuteWorker() {
        // to improve testability, we use a dummy Worker that just returns the event
        final Object result = this.workerExecuteApi.execute("exampleWorker", this.event);
        Assertions.assertEquals(this.event, result);
    }

    @Test
    void testExecuteWorkerWithMoreComplexEvent() {
        // to improve testability, we use a dummy Worker that just returns the event
        final Map<String, Object> event = Map.of(
                "test", "test",
                "test2", Map.of("test3", "test3"),
                "test4", Map.of("test5", Map.of("test6", "test6"))
        );
        final Object result = this.workerExecuteApi.execute("exampleWorker", event);
        Assertions.assertEquals(event, result);
    }

    @Test
    void testExecuteWorkerWithoutReturn() {
        // to improve testability, we use a dummy Worker that just returns the event
        final Object result = this.workerExecuteApi.execute("voidExampleWorker", this.event);
        Assertions.assertEquals(new HashMap<>(), result);
    }

    @Test
    void testExecuteWorkerThrowsRuntimeExceptionIfNoWorkerIsRegisteredForType() {
        Assertions.assertThrows(RuntimeException.class, () ->
                this.workerExecuteApi.execute("non-existing-type", this.event));
    }

    @Test
    void testExecuteWorkerThrowsTechnicalError() throws NoSuchMethodException {
        Assertions.assertThrows(TechnicalException.class, () ->
                this.workerExecuteApi.execute("technicalErrorWorker", this.event));
    }

    @Test
    void testExecuteWorkerThrowsBusinessException() throws NoSuchMethodException {
        Assertions.assertThrows(BusinessException.class, () ->
                this.workerExecuteApi.execute("businessErrorWorker", this.event));
    }

    // helpers

    private WorkerInfo getExampleWorker() throws NoSuchMethodException {
        class ExampleWorker {
            public Map<String, Object> exampleWorker(final Map<String, Object> event) {
                return event;
            }
        }
        return new WorkerInfo(
                "exampleWorker",
                3000L,
                new ExampleWorker(),
                ExampleWorker.class.getMethod("exampleWorker", Map.class),
                Map.class,
                Map.class
        );
    }

    private WorkerInfo getExampleWorkerWithoutReturn() throws NoSuchMethodException {
        class ExampleWorker {
            public void exampleWorker(final Map<String, Object> event) {

            }
        }
        return new WorkerInfo(
                "voidExampleWorker",
                3000L,
                new ExampleWorker(),
                ExampleWorker.class.getMethod("exampleWorker", Map.class),
                Map.class,
                Map.class
        );
    }

    private WorkerInfo getTechnicalExceptionIntegraton() throws NoSuchMethodException {
        class TechnicalErrorWorker {
            public Map<String, Object> technicalError(final Map<String, Object> event) {
                throw new TechnicalException("Technical Error");
            }
        }
        return new WorkerInfo(
                "technicalErrorWorker",
                3000L,
                new TechnicalErrorWorker(),
                TechnicalErrorWorker.class.getMethod("technicalError", Map.class),
                Map.class,
                Map.class
        );
    }

    private WorkerInfo getBusinessExceptionIntegraton() throws NoSuchMethodException {
        class BusinessErrorWorker {
            public Map<String, Object> businessError(final Map<String, Object> event) {
                throw new BusinessException("400", "Business Error");
            }
        }
        return new WorkerInfo(
                "businessErrorWorker",
                3000L,
                new BusinessErrorWorker(),
                BusinessErrorWorker.class.getMethod("businessError", Map.class),
                Map.class,
                Map.class
        );
    }

}
