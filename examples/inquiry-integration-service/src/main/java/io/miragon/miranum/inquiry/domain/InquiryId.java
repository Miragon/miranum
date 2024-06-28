package io.miragon.miranum.inquiry.domain;

import java.util.UUID;

public record InquiryId(
        UUID id
) {
    @Override
    public String toString(){
        return id.toString();
    }
}
