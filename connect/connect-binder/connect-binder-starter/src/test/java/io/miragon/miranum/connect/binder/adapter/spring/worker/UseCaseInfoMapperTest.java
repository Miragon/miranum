package io.miragon.miranum.connect.binder.adapter.spring.worker;

import io.miragon.miranum.connect.binder.worker.domain.Worker;
import io.miragon.miranum.connect.binder.worker.domain.WorkerInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

public class UseCaseInfoMapperTest {

    private final WorkerInfoMapper mapper = new WorkerInfoMapper();

    @Test
    public void givenDefaultUseCase_thenUseCaseInfoIsCreated() throws NoSuchMethodException {
        final Worker sendMessageUseCase = this.givenDefaultUseCase("sendMessage", 100L);
        final Object bean = new Object();
        final Method method = bean.getClass().getMethod("equals", Object.class);

        final WorkerInfo useCaseInfo = this.mapper.map(sendMessageUseCase, bean, method);

        assertEquals(bean, useCaseInfo.getInstance());
        assertEquals(method, useCaseInfo.getMethod());
        assertEquals("sendMessage", useCaseInfo.getType());
        assertEquals(100L, useCaseInfo.getTimeout());
        assertEquals(Object.class, useCaseInfo.getInputType());
    }

    @Test
    public void givenDefaultUseCaseWithNoInputParameter_thenUseCaseInfoIsCreated() throws NoSuchMethodException {
        final Worker sendMessageUseCase = this.givenDefaultUseCase("sendMessage", 100L);
        final Object bean = new Object();
        final Method method = bean.getClass().getMethod("toString");

        final WorkerInfo useCaseInfo = this.mapper.map(sendMessageUseCase, bean, method);

        assertEquals(bean, useCaseInfo.getInstance());
        assertEquals(method, useCaseInfo.getMethod());
        assertEquals("sendMessage", useCaseInfo.getType());
        assertEquals(100L, useCaseInfo.getTimeout());
        assertNull(useCaseInfo.getInputType());
    }

    @Test
    public void givenDefaultUseCaseWithMoreThanOneInputParameter_thenCreationFails() throws NoSuchMethodException {
        final Worker sendMessageUseCase = this.givenDefaultUseCase("sendMessage", 100L);
        final Object bean = new Object();
        final Method method = bean.getClass().getMethod("wait", long.class, int.class);

        final ToManyParametersExecption execption = Assertions.assertThrows(ToManyParametersExecption.class, () -> {
            this.mapper.map(sendMessageUseCase, bean, method);
        }, "NumberFormatException was expected");
    }

    public Worker givenDefaultUseCase(final String type, final Long timeout) {
        final Worker useCase = Mockito.mock(Worker.class);
        given(useCase.type()).willReturn(type);
        given(useCase.timeout()).willReturn(timeout);
        return useCase;
    }

}
