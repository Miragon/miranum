package io.miragon.miranum.inquiry.adapter.in.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Transport object to start a new process instance.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewInquiryDto {

    private String customerMail;
    private String projectDescription;
    private Integer estimatedConsultingHours;

}
