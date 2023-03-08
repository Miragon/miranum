package io.miragon.miranum.connect.adapter.in.c7.elementtemplates;

import io.miragon.miranum.connect.elementtemplate.api.GenerateElementTemplate;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class Test {
    @GenerateElementTemplate(name = "Test", id = "test", appliesTo = "bpmn:ServiceTask", version = "1.0.0")
    public TestOutput test(TestInput input) {
        return new TestOutput(input.getName(), input.getId());
    }
}

@Getter
class TestInput {
    private String name;
    private String id;
}

@AllArgsConstructor
class TestOutput {
    private String name;
    private String id;
}