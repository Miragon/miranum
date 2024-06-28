package io.miragon.miranum.inquiry.adapter.in.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CapacityCheckedDto {

    private String inquiryId;
    private boolean enoughCapacity;
    private String userTaskId;

}
