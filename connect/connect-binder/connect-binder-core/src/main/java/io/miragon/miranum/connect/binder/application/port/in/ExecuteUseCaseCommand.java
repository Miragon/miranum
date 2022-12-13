package io.miragon.miranum.connect.binder.application.port.in;

import io.miragon.miranum.connect.binder.domain.UseCaseInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class ExecuteUseCaseCommand {

    private final Object data;

    private final UseCaseInfo useCase;


}
