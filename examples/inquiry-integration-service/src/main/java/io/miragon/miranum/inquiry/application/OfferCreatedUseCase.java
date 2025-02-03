package io.miragon.miranum.inquiry.application;

import io.miragon.miranum.auth.api.UserAuthenticationProvider;
import io.miragon.miranum.connect.task.api.TaskApi;
import io.miragon.miranum.connect.task.api.command.CompleteTaskCommand;
import io.miragon.miranum.inquiry.application.port.in.OfferCreated;
import io.miragon.miranum.inquiry.application.port.in.model.OfferCreatedCommand;
import io.miragon.miranum.inquiry.application.port.out.InquiryRepository;
import io.miragon.miranum.inquiry.domain.Inquiry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class OfferCreatedUseCase implements OfferCreated {

    private final TaskApi taskApi;
    private final UserAuthenticationProvider authenticationProvider;
    private final InquiryRepository inquiryRepository;

    @Override
    public void handle(OfferCreatedCommand command) {
        // 1. load and update inquiry
        String currentUser = this.authenticationProvider.getLoggedInUser();
        Inquiry loaded = this.inquiryRepository.findById(command.inquiryId());
        Inquiry updated = loaded.update(command);
        Inquiry saved = this.inquiryRepository.save(updated);

        // 2. complete Task
        CompleteTaskCommand completeTaskCommand = CompleteTaskCommand.builder()
                .taskId(command.userTaskId())
                .variables(Collections.emptyMap())
                .build();
        this.taskApi.completeTask(completeTaskCommand, currentUser);

        log.info("[{}] Inquiry updated by offer: {}", saved.id(), saved);
    }

}
