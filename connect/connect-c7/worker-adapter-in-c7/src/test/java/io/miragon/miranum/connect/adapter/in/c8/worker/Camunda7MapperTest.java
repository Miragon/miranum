package io.miragon.miranum.connect.adapter.in.c8.worker;

import io.miragon.miranum.connect.adapter.in.c7.worker.Camunda7Mapper;
import io.miragon.miranum.connect.adapter.in.c8.worker.worker.Input;
import io.miragon.miranum.connect.adapter.in.c8.worker.worker.Output;
import lombok.extern.java.Log;
import org.camunda.bpm.client.variable.impl.value.JsonValueImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.impl.value.PrimitiveTypeValueImpl;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Log
public class Camunda7MapperTest {

    private final Camunda7Mapper camunda7Converter = new Camunda7Mapper();

    @Test
    public void convertInput() {
        final VariableMap data = Variables.createVariables()
                .putValueTyped("input", new PrimitiveTypeValueImpl.StringValueImpl("input"))
                .putValueTyped("inputObj", new JsonValueImpl("{\"input\":\"test\"}"));
        final Object result = this.camunda7Converter.mapInput(Input.class, data);
        assertTrue(result instanceof Input);
        final Input input = (Input) result;
        assertEquals(input.getInput(), "input");
        assertEquals(input.getInputObj().getInput(), "test");
    }

    @Test
    public void convertOutput() {
        final Output data = Output.builder()
                .output("output")
                .outputObj(Output.builder().output("test").build())
                .build();
        final Map<String, Object> result = this.camunda7Converter.mapOutput(data);
        assertEquals("output", result.get("output"));
        assertEquals("{outputObj=null, output=test}", result.get("outputObj"));
    }


}
