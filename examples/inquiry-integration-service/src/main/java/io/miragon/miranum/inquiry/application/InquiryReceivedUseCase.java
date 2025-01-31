package io.miragon.miranum.inquiry.application;

import io.miragon.miranum.auth.api.UserAuthenticationProvider;
import io.miragon.miranum.connect.process.api.ProcessApi;
import io.miragon.miranum.connect.process.api.StartProcessCommand;
import io.miragon.miranum.inquiry.application.port.in.InquiryReceived;
import io.miragon.miranum.inquiry.application.port.in.model.NewInquiryCommand;
import io.miragon.miranum.inquiry.application.port.out.InquiryRepository;
import io.miragon.miranum.inquiry.domain.Inquiry;
import io.miragon.miranum.inquiry.domain.InquiryId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class InquiryReceivedUseCase implements InquiryReceived {
    private final ProcessApi processApi;
    private final InquiryRepository inquiryRepository;
    private final UserAuthenticationProvider authenticationProvider;
    private final static String INQUIRY_PROCESS_KEY = "inquiry-process";

    @Override
    public InquiryId handle(NewInquiryCommand command) {
        // 1. save
        String currentUserId = this.authenticationProvider.getLoggedInUser();
        Inquiry saved = this.inquiryRepository.save(Inquiry.newInquiry(command, currentUserId));

        // 2. start process
        this.processApi.startProcess(
                StartProcessCommand.builder()
                     .processKey(INQUIRY_PROCESS_KEY)
                     .correlationKey(saved.id().toString())
                     .variables(Map.of("inquiryId", saved.id().toString()))
                     .build()
        );

        // 3. return generated id
        log.info("[{}] Inquiry created: {}", saved.id(), saved);
        return saved.id();
    }

}
