package io.miragon.miranum.connect.binder.adapter.camunda7;

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

    Camunda7Mapper camunda7Converter = new Camunda7Mapper();

    @Test
    public void convertInput() {
        final VariableMap data = Variables.createVariables()
                .putValueTyped("input", new PrimitiveTypeValueImpl.StringValueImpl("input"))
                .putValueTyped("inputObj", new JsonValueImpl("{\"input\":\"test\"}"));
        final MyJobWorker worker = new MyJobWorker();
        final Object result = this.camunda7Converter.convertInput(Input.class, data);
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
        final Map<String, Object> result = this.camunda7Converter.convertOutput(data);
        assertEquals("output", result.get("output"));
        assertEquals("{outputObj=null, output=test}", result.get("outputObj"));
    }


}
