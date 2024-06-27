package io.miragon.miranum.inquiry.domain;

import io.miragon.miranum.inquiry.application.port.in.model.CapacityCheckedCommand;
import io.miragon.miranum.inquiry.application.port.in.model.NewCustomerMailCommand;
import io.miragon.miranum.inquiry.application.port.in.model.NewInquiryCommand;
import io.miragon.miranum.inquiry.application.port.in.model.OfferCreatedCommand;
import jakarta.validation.Valid;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
@Builder
public record Inquiry(
        InquiryId id,
        String customerMail,
        String projectDescription,
        Integer estimatedConsultingHours,
        String createdBy,
        String capacityCheckedBy,
        Boolean enoughCapacity,
        Integer offerInEuros,
        Boolean offerAccepted
) {

    public static Inquiry newInquiry(@Valid NewInquiryCommand newInquiry, @NotNull String currentUserId) {
        return Inquiry.builder()
                .customerMail(newInquiry.customerMail())
                .projectDescription(newInquiry.projectDescription())
                .estimatedConsultingHours(newInquiry.estimatedConsultingHours())
                .createdBy(currentUserId)
                .build();
    }

    public Inquiry update(@Valid CapacityCheckedCommand command, @NotNull String currentUserId) {
        return Inquiry.builder()
                .id(this.id)
                .customerMail(this.customerMail)
                .projectDescription(this.projectDescription)
                .estimatedConsultingHours(this.estimatedConsultingHours)
                .createdBy(this.createdBy)
                .capacityCheckedBy(currentUserId)
                .enoughCapacity(command.enoughCapacity())
                .offerInEuros(this.offerInEuros)
                .offerAccepted(this.offerAccepted)
                .build();
    }

    public Inquiry update(@Valid OfferCreatedCommand command) {
        return Inquiry.builder()
                .id(this.id)
                .customerMail(this.customerMail)
                .projectDescription(this.projectDescription)
                .estimatedConsultingHours(this.estimatedConsultingHours)
                .createdBy(this.createdBy)
                .capacityCheckedBy(this.capacityCheckedBy)
                .enoughCapacity(this.enoughCapacity)
                .offerInEuros(command.offerInEuros())
                .offerAccepted(this.offerAccepted)
                .build();
    }

    public Inquiry update(@Valid NewCustomerMailCommand command) {
        return Inquiry.builder()
                .id(this.id)
                .customerMail(this.customerMail)
                .projectDescription(this.projectDescription)
                .estimatedConsultingHours(this.estimatedConsultingHours)
                .createdBy(this.createdBy)
                .capacityCheckedBy(this.capacityCheckedBy)
                .enoughCapacity(this.enoughCapacity)
                .offerInEuros(this.offerInEuros)
                .offerAccepted(command.isAccepted())
                .build();
    }
}
