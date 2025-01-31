package io.miragon.miranum.inquiry.application;

import dev.bpmcrafters.processengineapi.task.CompleteTaskCmd;
import dev.bpmcrafters.processengineapi.task.UserTaskCompletionApi;
import io.miragon.miranum.inquiry.application.port.in.CapacityChecked;
import io.miragon.miranum.inquiry.application.port.in.model.CapacityCheckedCommand;
import io.miragon.miranum.inquiry.application.port.out.InquiryRepository;
import io.miragon.miranum.inquiry.domain.Inquiry;
import io.miragon.miranum.platform.security.authentication.UserAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CapacityCheckedUseCase implements CapacityChecked {

    private final UserTaskCompletionApi taskCompletionApi;
    private final UserAuthenticationProvider authenticationProvider;
    private final InquiryRepository inquiryRepository;

    @SneakyThrows
    @Override
    public void handle(CapacityCheckedCommand command) {
        // 1. load and update inquiry
        String currentUser = this.authenticationProvider.getLoggedInUser();
        Inquiry loaded = this.inquiryRepository.findById(command.inquiryId());
        Inquiry updated = loaded.update(command, currentUser);
        Inquiry saved = this.inquiryRepository.save(updated);

        // 2. complete Task
        taskCompletionApi.completeTask(
                new CompleteTaskCmd(
                        command.userTaskId(),
                        () -> Map.of("enough_capacity", saved.enoughCapacity())
                )
        ).get();

        log.info("[{}] Inquiry updated by capacity check: {}", saved.id(), saved);
    }
}
