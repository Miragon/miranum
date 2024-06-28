package io.miragon.miranum.inquiry.adapter.out.persistance;

import io.miragon.miranum.inquiry.application.port.out.InquiryRepository;
import io.miragon.miranum.inquiry.domain.Inquiry;
import io.miragon.miranum.inquiry.domain.InquiryId;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.isNull;

@Component
public class InquiryRepositoryAdapter implements InquiryRepository {

    private final ConcurrentHashMap<String, Inquiry> dataStore = new ConcurrentHashMap<>();

    @Override
    public Inquiry findById(String id) {
        return this.dataStore.get(id);
    }

    @Override
    public Inquiry save(Inquiry inquiry) {
        if (isNull(inquiry.id())) {
            UUID uuid = UUID.randomUUID();
            Inquiry newInquiry = Inquiry.builder()
                    .id(new InquiryId(uuid))
                    .customerMail(inquiry.customerMail())
                    .projectDescription(inquiry.projectDescription())
                    .estimatedConsultingHours(inquiry.estimatedConsultingHours())
                    .createdBy(inquiry.createdBy())
                    .capacityCheckedBy(inquiry.capacityCheckedBy())
                    .enoughCapacity(inquiry.enoughCapacity())
                    .offerInEuros(inquiry.offerInEuros())
                    .offerAccepted(inquiry.offerAccepted())
                    .build();
            this.dataStore.put(newInquiry.id().toString(), newInquiry);
            return newInquiry;
        } else {
            this.dataStore.put(inquiry.id().toString(), inquiry);
            return inquiry;
        }
    }
}
