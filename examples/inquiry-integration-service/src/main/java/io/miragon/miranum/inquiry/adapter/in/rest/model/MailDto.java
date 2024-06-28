package io.miragon.miranum.inquiry.adapter.in.rest.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MailDto {

    private String inquiryId;
    private Boolean isAccepted;

}
