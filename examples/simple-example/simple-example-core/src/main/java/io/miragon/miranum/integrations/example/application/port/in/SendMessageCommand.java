package io.miragon.miranum.integrations.example.application.port.in;

import io.miragon.miranum.connect.elementtemplate.api.ElementTemplateProperty;
import io.miragon.miranum.connect.elementtemplate.api.PropertyType;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageCommand extends TenantAwareCommand {
    @ElementTemplateProperty(type = PropertyType.STRING, label = "content", editable = true)
    private String content;
    @ElementTemplateProperty(type = PropertyType.STRING, label = "key", editable = true)
    private String key;
}
