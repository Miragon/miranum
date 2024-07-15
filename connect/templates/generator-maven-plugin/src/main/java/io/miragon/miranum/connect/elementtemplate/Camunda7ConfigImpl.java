package io.miragon.miranum.connect.elementtemplate;

import io.miragon.miranum.connect.elementtemplate.core.Camunda7Configuration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Camunda7ConfigImpl implements Camunda7Configuration {
    private Boolean asyncBeforeDefaultValue = false;
    private Boolean asyncAfterDefaultValue = false;
}
