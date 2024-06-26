package io.miragon.miranum.inquiry.application.port.in;

import io.miragon.miranum.inquiry.application.port.in.model.NewInquiryCommand;
import io.miragon.miranum.inquiry.domain.InquiryId;

public interface InquiryReceived {
    InquiryId handle(NewInquiryCommand command);
}
