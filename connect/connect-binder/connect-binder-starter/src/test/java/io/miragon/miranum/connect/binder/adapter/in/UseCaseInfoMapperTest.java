package io.miragon.miranum.connect.binder.adapter.in;

import io.miragon.miranum.connect.binder.domain.UseCase;
import io.miragon.miranum.connect.binder.domain.UseCaseInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

public class UseCaseInfoMapperTest {

    private final UseCaseInfoMapper mapper = new UseCaseInfoMapper();

    @Test
    public void givenDefaultUseCase_thenUseCaseInfoIsCreated() throws NoSuchMethodException {
        final UseCase sendMessageUseCase = this.givenDefaultUseCase("sendMessage", 100L);
        final Object bean = new Object();
        final Method method = bean.getClass().getMethod("equals", Object.class);

        final UseCaseInfo useCaseInfo = this.mapper.map(sendMessageUseCase, bean, method);

        assertEquals(bean, useCaseInfo.getInstance());
        assertEquals(method, useCaseInfo.getMethod());
        assertEquals("sendMessage", useCaseInfo.getType());
        assertEquals(100L, useCaseInfo.getTimeout());
        assertEquals(Object.class, useCaseInfo.getInputType());
    }

    @Test
    public void givenDefaultUseCaseWithNoInputParameter_thenUseCaseInfoIsCreated() throws NoSuchMethodException {
        final UseCase sendMessageUseCase = this.givenDefaultUseCase("sendMessage", 100L);
        final Object bean = new Object();
        final Method method = bean.getClass().getMethod("toString");

        final UseCaseInfo useCaseInfo = this.mapper.map(sendMessageUseCase, bean, method);

        assertEquals(bean, useCaseInfo.getInstance());
        assertEquals(method, useCaseInfo.getMethod());
        assertEquals("sendMessage", useCaseInfo.getType());
        assertEquals(100L, useCaseInfo.getTimeout());
        assertNull(useCaseInfo.getInputType());
    }

    @Test
    public void givenDefaultUseCaseWithMoreThanOneInputParameter_thenCreationFails() throws NoSuchMethodException {
        final UseCase sendMessageUseCase = this.givenDefaultUseCase("sendMessage", 100L);
        final Object bean = new Object();
        final Method method = bean.getClass().getMethod("wait", long.class, int.class);

        final ToManyParametersExecption execption = Assertions.assertThrows(ToManyParametersExecption.class, () -> {
            this.mapper.map(sendMessageUseCase, bean, method);
        }, "NumberFormatException was expected");
    }

    public UseCase givenDefaultUseCase(final String type, final Long timeout) {
        final UseCase useCase = Mockito.mock(UseCase.class);
        given(useCase.type()).willReturn(type);
        given(useCase.timeout()).willReturn(timeout);
        return useCase;
    }

}
