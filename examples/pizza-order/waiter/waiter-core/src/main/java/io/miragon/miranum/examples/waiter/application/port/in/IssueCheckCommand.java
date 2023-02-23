package io.miragon.miranum.examples.waiter.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class IssueCheckCommand {

    private List<String> drinks;
    private List<String> food;
}
