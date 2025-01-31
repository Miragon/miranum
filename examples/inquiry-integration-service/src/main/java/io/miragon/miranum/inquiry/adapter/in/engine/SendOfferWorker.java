package io.miragon.miranum.inquiry.adapter.in.engine;

import dev.bpmcrafters.processengine.worker.ProcessEngineWorker;
import dev.bpmcrafters.processengine.worker.Variable;
import io.miragon.miranum.connect.elementtemplate.api.ElementTemplate;
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

    @ProcessEngineWorker(topic = "send-offer")
    @ElementTemplate(name = "Inquiry - Send Offer")
    public void sendOffer(@Variable(name = "inquiryId") InquiryDto dto) {
        this.sendOffer.handle(new SendCommand(
            dto.getInquiryId(),
            SendType.OFFER
        ));
    }
}
