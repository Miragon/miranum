package io.miragon.miranum.inquiry.application;

import dev.bpmcrafters.processengineapi.correlation.CorrelateMessageCmd;
import dev.bpmcrafters.processengineapi.correlation.Correlation;
import dev.bpmcrafters.processengineapi.correlation.CorrelationApi;
import io.miragon.miranum.inquiry.application.port.in.CustomerMailReceived;
import io.miragon.miranum.inquiry.application.port.in.model.NewCustomerMailCommand;
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
public class CustomerMailReceivedUseCase implements CustomerMailReceived {
    private final CorrelationApi correlationApi;
    private final InquiryRepository inquiryRepository;

    private static final String OFFER_ACCEPTED_MESSAGE_NAME = "offer-accepted";
    private static final String OFFER_DECLINED_MESSAGE_NAME = "offer-declined";

    @SneakyThrows
    @Override
    public void handle(NewCustomerMailCommand command) {
        // 1. load and update inquiry
        Inquiry loaded = this.inquiryRepository.findById(command.inquiryId());
        Inquiry updated = loaded.update(command);
        Inquiry saved = this.inquiryRepository.save(updated);

        // 2. Correlate Message
        correlationApi.correlateMessage(
            new CorrelateMessageCmd(
                saved.offerAccepted()
                        ? OFFER_ACCEPTED_MESSAGE_NAME
                        : OFFER_DECLINED_MESSAGE_NAME,
                Collections::emptyMap,
                () -> Correlation.withKey(saved.id().toString())
            )
        ).get();

        log.info("[{}] Inquiry updated by customer mail: {}", saved.id(), saved);
    }
}
