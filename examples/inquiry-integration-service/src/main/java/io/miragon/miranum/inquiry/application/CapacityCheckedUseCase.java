package io.miragon.miranum.inquiry.application;

import io.miragon.miranum.auth.api.UserAuthenticationProvider;
import io.miragon.miranum.connect.task.api.TaskApi;
import io.miragon.miranum.connect.task.api.command.CompleteTaskCommand;
import io.miragon.miranum.inquiry.application.port.in.CapacityChecked;
import io.miragon.miranum.inquiry.application.port.in.model.CapacityCheckedCommand;
import io.miragon.miranum.inquiry.application.port.out.InquiryRepository;
import io.miragon.miranum.inquiry.domain.Inquiry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CapacityCheckedUseCase implements CapacityChecked {

    private final TaskApi taskApi;
    private final UserAuthenticationProvider authenticationProvider;
    private final InquiryRepository inquiryRepository;

    @Override
    public void handle(CapacityCheckedCommand command) {
        // 1. load and update inquiry
        String currentUser = this.authenticationProvider.getLoggedInUser();
        Inquiry loaded = this.inquiryRepository.findById(command.inquiryId());
        Inquiry updated = loaded.update(command, currentUser);
        Inquiry saved = this.inquiryRepository.save(updated);

        // 2. complete Task
        CompleteTaskCommand completeTaskCommand = CompleteTaskCommand.builder()
                .taskId(command.userTaskId())
                .variables(Map.of("enough_capacity", saved.enoughCapacity()))
                .build();
        this.taskApi.completeTask(completeTaskCommand, currentUser);

        log.info("[{}] Inquiry updated by capacity check: {}", saved.id(), saved);
    }
}
