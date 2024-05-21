package io.miragon.miranum.platform.tasklist.adapter.in.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class CompleteTaskDto {

    private Map<String, Object> payload;

}
