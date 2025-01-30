package io.miragon.miranum.inquiry.application.port.in.model;

import jakarta.validation.constraints.NotNull;

public record NewCustomerMailCommand(
        @NotNull String inquiryId,
        @NotNull Boolean isAccepted) {
}
