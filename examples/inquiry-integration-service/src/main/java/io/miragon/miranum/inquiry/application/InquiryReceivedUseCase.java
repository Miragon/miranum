package io.miragon.miranum.inquiry.application;

import dev.bpmcrafters.processengineapi.process.StartProcessApi;
import dev.bpmcrafters.processengineapi.process.StartProcessByDefinitionCmd;
import io.miragon.miranum.inquiry.application.port.in.InquiryReceived;
import io.miragon.miranum.inquiry.application.port.in.model.NewInquiryCommand;
import io.miragon.miranum.inquiry.application.port.out.InquiryRepository;
import io.miragon.miranum.inquiry.domain.Inquiry;
import io.miragon.miranum.inquiry.domain.InquiryId;
import io.miragon.miranum.platform.security.authentication.UserAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class InquiryReceivedUseCase implements InquiryReceived {
    private final StartProcessApi startProcessApi;
    private final InquiryRepository inquiryRepository;
    private final UserAuthenticationProvider authenticationProvider;
    private final static String INQUIRY_PROCESS_KEY = "inquiry-process";

    @Override
    @SneakyThrows
    public InquiryId handle(NewInquiryCommand command) {
        // 1. save
        String currentUserId = this.authenticationProvider.getLoggedInUser();
        Inquiry saved = this.inquiryRepository.save(Inquiry.newInquiry(command, currentUserId));

        // 2. start process
        startProcessApi.startProcess(
                new StartProcessByDefinitionCmd(
                        INQUIRY_PROCESS_KEY,
                        () -> Map.of("inquiryId", saved.id().toString())
                )
        ).get();

        // 3. return generated id
        log.info("[{}] Inquiry created: {}", saved.id(), saved);
        return saved.id();
    }

}
