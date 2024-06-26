package io.miragon.miranum.inquiry.application.port.in.model;

import org.jetbrains.annotations.NotNull;

public record OfferCreatedCommand(
        @NotNull String inquiryId,
        @NotNull Integer offerInEuros,
        @NotNull String userTaskId) {
}
