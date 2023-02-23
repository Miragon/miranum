package io.miragon.miranum.examples.notification.adapter.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NotifyGuestCommand {

    private String name;
    private String email;
    private List<String> food;
    private List<String> drinks;
}