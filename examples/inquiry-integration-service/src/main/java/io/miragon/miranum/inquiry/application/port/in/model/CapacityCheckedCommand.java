package io.miragon.miranum.inquiry.application.port.in.model;

import jakarta.validation.constraints.NotNull;

public record CapacityCheckedCommand(
        @NotNull String inquiryId,
        @NotNull Boolean enoughCapacity,
        @NotNull String userTaskId) {
}
