package io.miragon.miranum.integrations.example.application.port.in;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageCommand extends TenantAwareCommand {

    private String name;
    private String key;


}
