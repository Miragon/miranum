package io.miragon.miranum.inquiry.application;

import io.miragon.miranum.inquiry.application.port.in.SendOffer;
import io.miragon.miranum.inquiry.application.port.in.model.SendCommand;
import io.miragon.miranum.inquiry.application.port.out.InquiryRepository;
import io.miragon.miranum.inquiry.domain.Inquiry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendOfferUseCase implements SendOffer {

    private final InquiryRepository inquiryRepository;
    @Override
    public void handle(SendCommand command) {
        Inquiry loaded = this.inquiryRepository.findById(command.inquiryId());
        log.info("[{}] {} sent: {}", loaded.id(), command.type(), loaded);
    }
}
