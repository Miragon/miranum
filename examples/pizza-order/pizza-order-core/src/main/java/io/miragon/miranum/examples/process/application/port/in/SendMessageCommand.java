package io.miragon.miranum.examples.process.application.port.in;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageCommand {

    private String name;
    private String key;


}
