package io.miragon.miranum.inquiry.application.port.in.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record NewInquiryCommand(
        @NotNull String customerMail,
        @Nullable String projectDescription,
        @NotNull Integer estimatedConsultingHours) {
}
