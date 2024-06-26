package io.miragon.miranum.inquiry.application.port.in.model;

import org.jetbrains.annotations.NotNull;

public record CapacityCheckedCommand(
        @NotNull String inquiryId,
        @NotNull Boolean enoughCapacity,
        @NotNull String userTaskId) {
}
