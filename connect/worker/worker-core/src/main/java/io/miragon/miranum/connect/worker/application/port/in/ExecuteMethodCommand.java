package io.miragon.miranum.connect.binder.worker.application.port.in;

import io.miragon.miranum.connect.binder.worker.domain.WorkerInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class ExecuteMethodCommand {

    private final Object data;

    private final WorkerInfo worker;


}
