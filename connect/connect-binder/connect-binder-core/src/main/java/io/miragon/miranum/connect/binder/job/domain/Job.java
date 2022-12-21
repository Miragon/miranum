package io.miragon.miranum.connect.binder.job.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class Job {

    String key;

    Map<String, Object> variables;

}
