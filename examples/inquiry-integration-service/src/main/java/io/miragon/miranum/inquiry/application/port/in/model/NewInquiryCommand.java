package io.miragon.miranum.inquiry.application.port.in.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record NewInquiryCommand(
        @NotNull String customerMail,
        @Nullable String projectDescription,
        @NotNull Integer estimatedConsultingHours) {
}
