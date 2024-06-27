package io.miragon.miranum.inquiry.adapter.in.engine;

import io.miragon.miranum.connect.elementtemplate.api.ElementTemplate;
import io.miragon.miranum.connect.worker.api.Worker;
import io.miragon.miranum.inquiry.adapter.in.engine.model.InquiryDto;
import io.miragon.miranum.inquiry.application.port.in.SendOffer;
import io.miragon.miranum.inquiry.application.port.in.model.SendCommand;
import io.miragon.miranum.inquiry.application.port.in.model.SendType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendRejectionWorker {

    private final SendOffer sendOffer;

    @Worker(type = "send-rejection")
    @ElementTemplate(name = "Inquiry - Send Rejection")
    public void sendRejection(InquiryDto dto) {
        this.sendOffer.handle(new SendCommand(
                dto.getInquiryId(),
                SendType.REJECTION
        ));
    }
}
