package io.miragon.miranum.connect.elementtemplate;

import io.miragon.miranum.connect.adapter.in.c7.elementtemplates.Camunda7ElementTemplateGenerator;
import io.miragon.miranum.connect.elementtemplate.api.ElementTemplateGenerator;

public class ElementTemplateGeneratorFactory {

    public static ElementTemplateGenerator[] create(TargetPlatform[] targetPlatform) {
        ElementTemplateGenerator[] generators = new ElementTemplateGenerator[targetPlatform.length];
        for (int i = 0; i < targetPlatform.length; i++) {
            generators[i] = create(targetPlatform[i]);
        }
        return generators;
    }

    public static ElementTemplateGenerator create(TargetPlatform targetPlatform) {
        switch (targetPlatform) {
            case camunda7 -> {
                return new Camunda7ElementTemplateGenerator();
            }
            case camunda8 -> throw new UnsupportedOperationException("Camunda 8 is not supported yet");
        }
        throw new IllegalArgumentException("Unknown target platform: " + targetPlatform);
    }
}