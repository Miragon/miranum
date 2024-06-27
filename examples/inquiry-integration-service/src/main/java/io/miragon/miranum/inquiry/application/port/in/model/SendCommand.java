package io.miragon.miranum.inquiry.application.port.in.model;

public record SendCommand(
        String inquiryId,
        SendType type
){
}
