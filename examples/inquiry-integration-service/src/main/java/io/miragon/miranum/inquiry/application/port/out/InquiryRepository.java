package io.miragon.miranum.inquiry.application.port.out;

import io.miragon.miranum.inquiry.domain.Inquiry;

public interface InquiryRepository {
    Inquiry findById(String id);
    Inquiry save(Inquiry inquiry);
}
