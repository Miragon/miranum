package io.miragon.miranum.platform.adapter.in.engine.worker.model;

import io.miragon.miranum.connect.elementtemplate.api.ElementTemplateProperty;
import io.miragon.miranum.connect.elementtemplate.api.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InquiryDto {

    @ElementTemplateProperty(type = PropertyType.STRING)
    private String inquiryId;
}
