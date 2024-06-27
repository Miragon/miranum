package io.miragon.miranum.inquiry.adapter.in.rest.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewInquiryDto {

    private String customerMail;
    private String projectDescription;
    private Integer estimatedConsultingHours;

}
