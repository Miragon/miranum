package io.miragon.miranum.platform.example.application.domain;

import lombok.Builder;

import java.util.List;

@Builder
public record SendNotification(
    List<String> receivers,
    String subject,
    String body
) { }
