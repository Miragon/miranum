package io.miragon.miranum.inquiry.adapter.in.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailDto {

    private String inquiryId;
    private boolean isAccepted;

}
