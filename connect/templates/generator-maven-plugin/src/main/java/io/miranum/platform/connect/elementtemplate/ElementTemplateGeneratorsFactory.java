package io.miranum.platform.connect.elementtemplate;

import io.miranum.platform.connect.elementtemplate.c7.Camunda7ElementTemplateGenerator;
import io.miranum.platform.connect.elementtemplate.c8.Camunda8ElementTemplateGenerator;
import io.miranum.platform.connect.elementtemplate.core.ElementTemplateGenerator;
import io.miranum.platform.connect.elementtemplate.core.TargetPlatform;

import java.util.ArrayList;
import java.util.List;

public class ElementTemplateGeneratorsFactory {

    public static List<ElementTemplateGenerator> create(TargetPlatform[] targetPlatform) {
        var generators = new ArrayList<ElementTemplateGenerator>(targetPlatform.length);
        for (var platform : targetPlatform) {
            generators.add(create(platform));
        }
        return generators;
    }

    public static ElementTemplateGenerator create(TargetPlatform targetPlatform) {
        switch (targetPlatform) {
            case camunda7 -> {
                return new Camunda7ElementTemplateGenerator();
            }
            case camunda8 -> {
                return new Camunda8ElementTemplateGenerator();
            }
        }
        throw new IllegalArgumentException("Unknown target platform: " + targetPlatform);
    }
}
