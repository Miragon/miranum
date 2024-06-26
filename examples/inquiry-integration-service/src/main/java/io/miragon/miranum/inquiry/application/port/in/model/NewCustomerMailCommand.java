package io.miragon.miranum.inquiry.application.port.in.model;

import org.jetbrains.annotations.NotNull;

public record NewCustomerMailCommand(
        @NotNull String inquiryId,
        @NotNull Boolean accepted) {
}
