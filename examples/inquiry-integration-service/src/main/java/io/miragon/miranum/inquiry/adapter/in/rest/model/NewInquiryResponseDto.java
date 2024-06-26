package io.miragon.miranum.inquiry.adapter.in.rest.model;

import lombok.Builder;

/**
 * Transport object to start a new process instance.
 */
@Builder
public class NewInquiryResponseDto {
    private String inquiryId;
}
