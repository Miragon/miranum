package io.miragon.miranum.connect.elementtemplate.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class GenerateElementTemplatesCommand {

    private List<ElementTemplateInfo> elementTemplateInfos;
}