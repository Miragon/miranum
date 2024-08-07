package io.miragon.miranum.connect.elementtemplate;

import io.miragon.miranum.connect.elementtemplate.c7.Camunda7ElementTemplateGenerator;
import io.miragon.miranum.connect.elementtemplate.c8.Camunda8ElementTemplateGenerator;
import io.miragon.miranum.connect.elementtemplate.core.ElementTemplateGenerator;
import io.miragon.miranum.connect.elementtemplate.core.TargetPlatform;

import java.util.ArrayList;
import java.util.List;

public class ElementTemplateGeneratorsFactory {

    public static List<ElementTemplateGenerator> create(TargetPlatform[] targetPlatform, PlatformSpecificConfig platformSpecificConfig) {
        var generators = new ArrayList<ElementTemplateGenerator>(targetPlatform.length);
        for (var platform : targetPlatform) {
            generators.add(create(platform, platformSpecificConfig));
        }
        return generators;
    }

    public static ElementTemplateGenerator create(TargetPlatform targetPlatform, PlatformSpecificConfig platformSpecificConfig) {
        switch (targetPlatform) {
            case C7 -> {
                return new Camunda7ElementTemplateGenerator(platformSpecificConfig.getC7());
            }
            case C8 -> {
                return new Camunda8ElementTemplateGenerator();
            }
        }
        throw new IllegalArgumentException("Unknown target platform: " + targetPlatform);
    }
}
