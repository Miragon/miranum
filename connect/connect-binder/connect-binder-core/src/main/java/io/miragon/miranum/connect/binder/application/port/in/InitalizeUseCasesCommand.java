package io.miragon.miranum.connect.binder.application.port.in;

import io.miragon.miranum.connect.binder.domain.UseCaseInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class InitalizeUseCasesCommand {

    private final List<UseCaseInfo> useCases;

}
