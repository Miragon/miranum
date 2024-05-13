package io.miragon.miranum.platform.connect.camunda7.remote.worker.worker;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Output {

    Output outputObj;

    private String output;
}
