package io.miragon.miranum.connect.binder.adapter.kafka;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BpmnErrorEvent {

    private String processInstanceId;

    @NotBlank
    private String messageName;

    @NotBlank
    private String errorCode;

    private String errorMessage;

}
