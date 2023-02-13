package io.miragon.miranum.connect.worker.impl;

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
