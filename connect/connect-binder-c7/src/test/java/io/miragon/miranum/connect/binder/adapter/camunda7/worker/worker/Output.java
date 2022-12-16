package io.miragon.miranum.connect.binder.adapter.camunda7.worker.worker;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Output {

    Output outputObj;

    private String output;
}
