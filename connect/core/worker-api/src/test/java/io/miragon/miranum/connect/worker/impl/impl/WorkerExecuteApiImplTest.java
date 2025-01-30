package io.miragon.miranum.connect.worker.impl.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.miragon.miranum.connect.worker.api.BusinessException;
import io.miragon.miranum.connect.worker.api.TechnicalException;
import io.miragon.miranum.connect.worker.api.WorkerExecuteApi;
import io.miragon.miranum.connect.worker.api.WorkerInterceptor;
import io.miragon.miranum.connect.worker.impl.WorkerExecuteApiImpl;
import io.miragon.miranum.connect.worker.impl.WorkerExecutor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class WorkerExecuteApiImplTest {

    private final WorkerInterceptor interceptor = Mockito.mock(WorkerInterceptor.class);
    private final WorkerExecutor workerExecutor = Mockito.mock(WorkerExecutor.class);
    private final WorkerExecuteApi workerExecuteApi = new WorkerExecuteApiImpl(List.of());

    // test data
    private final Map<String, Object> event = Map.ofEntries(
            entry("test", "test"),
            entry("test2", "test2")
    );

    WorkerExecuteApiImplTest() {
        Mockito.doAnswer(invocation -> null).when(interceptor).intercept(any(), any());
    }

    @Test
    void testExecuteWorker() throws InvocationTargetException, IllegalAccessException {
        when(this.workerExecutor.getType()).thenReturn("exampleWorker");
        doReturn(Map.class).when(this.workerExecutor).getInputType();
        doReturn(Map.class).when(this.workerExecutor).getOutputType();
        when(this.workerExecutor.execute(this.event)).thenReturn(this.event);

        final Object result = this.workerExecuteApi.execute(this.workerExecutor, this.event);
        assertEquals(this.event, result);

        // make sure that the worker was called
        final ArgumentCaptor<Map<String, Object>> inputArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        Mockito.verify(this.workerExecutor).execute(inputArgumentCaptor.capture());
        assertEquals(this.event, inputArgumentCaptor.getValue());
    }

    @Test
    void testExecuteWorker_withInterceptor() throws InvocationTargetException, IllegalAccessException {
        final WorkerExecuteApiImpl workerExecuteApi = new WorkerExecuteApiImpl(List.of(this.interceptor));

        when(this.workerExecutor.getType()).thenReturn("exampleWorker");
        doReturn(Map.class).when(this.workerExecutor).getInputType();
        doReturn(Map.class).when(this.workerExecutor).getOutputType();
        when(this.workerExecutor.execute(this.event)).thenReturn(this.event);

        final Object result = workerExecuteApi.execute(this.workerExecutor, this.event);
        assertEquals(this.event, result);

        // make sure that the interceptor was called
        final ArgumentCaptor<WorkerExecutor> workerExecutorArgumentCaptor = ArgumentCaptor.forClass(WorkerExecutor.class);
        final ArgumentCaptor<Map<String, Object>> interceptorArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        Mockito.verify(this.interceptor).intercept(workerExecutorArgumentCaptor.capture(), interceptorArgumentCaptor.capture());
        assertEquals(this.event, interceptorArgumentCaptor.getValue());
    }

    @Test
    void testExecuteWorker_throwsTechnicalError() throws InvocationTargetException, IllegalAccessException {
        when(this.workerExecutor.getType()).thenReturn("exampleWorker");
        doReturn(Map.class).when(this.workerExecutor).getInputType();
        doReturn(Map.class).when(this.workerExecutor).getOutputType();
        when(this.workerExecutor.execute(this.event)).thenThrow(new TechnicalException("error msg"));

        Assertions.assertThrows(TechnicalException.class, () ->
                this.workerExecuteApi.execute(this.workerExecutor, this.event));
    }

    @Test
    void testExecuteWorker_throwsBusinessException() throws InvocationTargetException, IllegalAccessException {
        when(this.workerExecutor.getType()).thenReturn("exampleWorker");
        doReturn(Map.class).when(this.workerExecutor).getInputType();
        doReturn(Map.class).when(this.workerExecutor).getOutputType();
        when(this.workerExecutor.execute(this.event)).thenThrow(new BusinessException("400", "error msg"));

        Assertions.assertThrows(BusinessException.class, () ->
                this.workerExecuteApi.execute(this.workerExecutor, this.event));
    }

    @Test
    void testExecuteWorker_withNullInputType_shouldNotThrowException() throws InvocationTargetException, IllegalAccessException {
        when(this.workerExecutor.getType()).thenReturn("exampleWorker");
        doReturn(null).when(this.workerExecutor).getInputType();
        doReturn(Map.class).when(this.workerExecutor).getOutputType();
        when(this.workerExecutor.execute(null)).thenReturn(this.event);

        final Object result = this.workerExecuteApi.execute(this.workerExecutor, null);
        assertEquals(this.event, result);
    }

    @Test
    void testExecute_withValidComplexInputObjectC8() throws IllegalAccessException, InvocationTargetException {
        // Arrange
        ComplexInputObject inputObject = new ComplexInputObject(
                "John", "Doe",
                List.of("Role1", "Role2"),
                Map.ofEntries(
                        entry("key1", "value1"),
                        entry("key2", "value2")
                )
        );
        Map<String, Object> expectedOutput = Map.of("result", "success");

        when(this.workerExecutor.getInputType()).thenReturn((Class) ComplexInputObject.class);
        when(this.workerExecutor.execute(any())).thenReturn(expectedOutput);

        WorkerInterceptor interceptor = Mockito.mock(WorkerInterceptor.class);
        List<WorkerInterceptor> interceptors = List.of(interceptor);
        WorkerExecuteApiImpl workerExecuteApiImpl = new WorkerExecuteApiImpl(interceptors);

        // Act
        Map<String, Object> result = workerExecuteApiImpl.execute(this.workerExecutor, inputObject);

        // Assert
        assertNotNull(result);
        assertEquals(expectedOutput, result);
        Mockito.verify(interceptor, Mockito.times(1)).intercept(any(), any());
        Mockito.verify(this.workerExecutor, Mockito.times(1)).execute(any());
    }

    @Test
    void testExecute_throwsRuntimeExceptionOnIllegalAccessException() throws IllegalAccessException, InvocationTargetException {
        // Arrange
        when(this.workerExecutor.getInputType()).thenReturn((Class) ComplexInputObject.class);
        when(this.workerExecutor.execute(any())).thenThrow(IllegalAccessException.class);

        // Act & Assert
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            workerExecuteApi.execute(this.workerExecutor, new ComplexInputObject(
                    "John", "Doe",
                    List.of("Role1", "Role2"),
                    Map.ofEntries(
                            entry("key1", "value1"),
                            entry("key2", "value2")
                    )
            ));
        });

        assertEquals(IllegalAccessException.class, exception.getCause().getClass());
    }

    @Test
    void testExecute_withNestedComplexInputObjectC8() throws IllegalAccessException, InvocationTargetException, JsonProcessingException {
        // Arrange
        ComplexInputObject nestedObject = new ComplexInputObject(
                "Jane", "Doe",
                List.of("NestedRole1", "NestedRole2"),
                Map.ofEntries(
                        entry("nestedKey1", "nestedValue1"),
                        entry("nestedKey2", "nestedValue2")
                )
        );

        ComplexInputObject inputObject = new ComplexInputObject(
                "John", "Doe",
                List.of("Role1", "Role2"),
                Map.ofEntries(
                        entry("key1", "value1"),
                        entry("key2", "value2"),
                        entry("nestedObject", nestedObject.toString())
                )
        );

        Map<String, Object> expectedOutput = Map.of("result", "success");

        when(this.workerExecutor.getInputType()).thenReturn((Class) ComplexInputObject.class);
        when(this.workerExecutor.execute(any())).thenReturn(expectedOutput);

        WorkerInterceptor interceptor = Mockito.mock(WorkerInterceptor.class);
        List<WorkerInterceptor> interceptors = List.of(interceptor);
        WorkerExecuteApiImpl workerExecuteApiImpl = new WorkerExecuteApiImpl(interceptors);

        // Act
        Map<String, Object> result = workerExecuteApiImpl.execute(this.workerExecutor, inputObject);

        // Assert
        assertNotNull(result);
        assertEquals(expectedOutput, result);
        Mockito.verify(interceptor, Mockito.times(1)).intercept(any(), any());
        Mockito.verify(this.workerExecutor, Mockito.times(1)).execute(any());
    }

    @Test
    void testMapInput_withNestedComplexInputInnerListObjectC8() throws InvocationTargetException, IllegalAccessException {
        // Define the complex input object with the nested object included directly
        ObjectWithInnerObjectList inputObject = new ObjectWithInnerObjectList(
                "John",
                List.of(new NestedObject("hi", "dude"),
                        new NestedObject("whats", "up"),
                        new NestedObject("2", "night")));

        Map<String, Object> expectedOutput = Map.of("result", "success");

        when(this.workerExecutor.getInputType()).thenReturn((Class) ObjectWithInnerObjectList.class);
        when(this.workerExecutor.execute(any())).thenReturn(expectedOutput);

        WorkerInterceptor interceptor = Mockito.mock(WorkerInterceptor.class);
        List<WorkerInterceptor> interceptors = List.of(interceptor);
        WorkerExecuteApiImpl workerExecuteApiImpl = new WorkerExecuteApiImpl(interceptors);

        // Act
        Map<String, Object> result = workerExecuteApiImpl.execute(this.workerExecutor, inputObject);

        // Assert
        assertNotNull(result);
        assertEquals(expectedOutput, result);
        Mockito.verify(interceptor, Mockito.times(1)).intercept(any(), any());
        Mockito.verify(this.workerExecutor, Mockito.times(1)).execute(any());
    }

    @Test
    void testMapInput_withNestedComplexInputInnerMapObjectC8() throws InvocationTargetException, IllegalAccessException {
        // Define the complex input object with the nested object included directly
        ObjectWithInnerObjectMap inputObject = new ObjectWithInnerObjectMap(
                "John",
                Map.ofEntries(
                        entry("1", new NestedObject("Hi", "It's me")),
                        entry("2", new NestedObject("How", "Are You"))
                ));

        Map<String, Object> expectedOutput = Map.of("result", "success");

        when(this.workerExecutor.getInputType()).thenReturn((Class) ObjectWithInnerObjectMap.class);
        when(this.workerExecutor.execute(any())).thenReturn(expectedOutput);

        WorkerInterceptor interceptor = Mockito.mock(WorkerInterceptor.class);
        List<WorkerInterceptor> interceptors = List.of(interceptor);
        WorkerExecuteApiImpl workerExecuteApiImpl = new WorkerExecuteApiImpl(interceptors);

        // Act
        Map<String, Object> result = workerExecuteApiImpl.execute(this.workerExecutor, inputObject);

        // Assert
        assertNotNull(result);
        assertEquals(expectedOutput, result);
        Mockito.verify(interceptor, Mockito.times(1)).intercept(any(), any());
        Mockito.verify(this.workerExecutor, Mockito.times(1)).execute(any());
    }


    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class ComplexInputObject {
        private String firstName;
        private String lastName;
        private List<String> roles;
        private Map<String, String> attributes;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class ObjectWithInnerObjectList {
        private String name;
        private List<NestedObject> attributes;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class ObjectWithInnerObjectMap {
        private String name;
        private Map<String, NestedObject> attributes;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class NestedObject {
        private String nestedField1;
        private String nestedField2;
    }
}
