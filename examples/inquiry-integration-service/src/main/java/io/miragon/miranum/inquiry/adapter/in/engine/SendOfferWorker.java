package io.miragon.miranum.inquiry.adapter.in.engine;

import io.miragon.miranum.connect.elementtemplate.api.ElementTemplate;
import io.miragon.miranum.connect.worker.api.Worker;
import io.miragon.miranum.inquiry.adapter.in.engine.model.InquiryDto;
import io.miragon.miranum.inquiry.application.port.in.SendOffer;
import io.miragon.miranum.inquiry.application.port.in.model.SendCommand;
import io.miragon.miranum.inquiry.application.port.in.model.SendType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendOfferWorker {

    private final SendOffer sendOffer;

    @Worker(type = "send-offer")
    @ElementTemplate(name = "Inquiry - Send Offer")
    public void sendOffer(InquiryDto dto) {
        this.sendOffer.handle(new SendCommand(
                dto.getInquiryId(),
                SendType.OFFER
        ));
    }
}
