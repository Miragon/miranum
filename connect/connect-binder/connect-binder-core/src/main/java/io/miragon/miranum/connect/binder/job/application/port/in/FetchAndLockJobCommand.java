package io.miragon.miranum.connect.binder.job.application.port.in;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FetchAndLockJobCommand {

    private String type;

    private String worker;
    
    private String timeout;


}
