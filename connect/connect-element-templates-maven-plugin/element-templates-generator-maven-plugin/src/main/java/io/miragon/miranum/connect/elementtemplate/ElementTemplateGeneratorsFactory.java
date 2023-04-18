package io.miragon.miranum.connect.elementtemplate;

import io.miragon.miranum.connect.adapter.in.c7.elementtemplates.Camunda7ElementTemplateGenerator;
import io.miragon.miranum.connect.elementtemplate.core.ElementTemplateGenerator;
import io.miragon.miranum.connect.elementtemplate.core.TargetPlatform;

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
            case camunda8 -> throw new UnsupportedOperationException("Camunda 8 is not supported yet");
        }
        throw new IllegalArgumentException("Unknown target platform: " + targetPlatform);
    }
}