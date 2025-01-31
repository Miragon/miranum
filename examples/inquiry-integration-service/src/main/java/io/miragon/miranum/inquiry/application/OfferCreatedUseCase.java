package io.miragon.miranum.inquiry.application;

import dev.bpmcrafters.processengineapi.task.CompleteTaskCmd;
import dev.bpmcrafters.processengineapi.task.UserTaskCompletionApi;
import io.miragon.miranum.inquiry.application.port.in.OfferCreated;
import io.miragon.miranum.inquiry.application.port.in.model.OfferCreatedCommand;
import io.miragon.miranum.inquiry.application.port.out.InquiryRepository;
import io.miragon.miranum.inquiry.domain.Inquiry;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class OfferCreatedUseCase implements OfferCreated {

    private final UserTaskCompletionApi taskCompletionApi;
    private final InquiryRepository inquiryRepository;

    @SneakyThrows
    @Override
    public void handle(OfferCreatedCommand command) {
        // 1. load and update inquiry
        Inquiry loaded = this.inquiryRepository.findById(command.inquiryId());
        Inquiry updated = loaded.update(command);
        Inquiry saved = this.inquiryRepository.save(updated);

        // 2. complete Task
        taskCompletionApi.completeTask(
            new CompleteTaskCmd(
                    command.userTaskId(),
                    Collections::emptyMap
            )
        ).get();

        log.info("[{}] Inquiry updated by offer: {}", saved.id(), saved);
    }

}
