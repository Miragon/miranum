package io.miragon.miranum.connect.jsonschema.api;

public interface JsonSchema {

    void validateData(Object data);
    
    void mergeData(Object source, Object target);


}
