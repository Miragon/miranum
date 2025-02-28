package io.miragon.miranum.platform.engine.impl;

import io.miragon.miranum.platform.engine.api.MiranumProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Map between {@link MiranumProcessDefinition} and {@link ProcessDefinition}
 */
@Mapper
public interface MiranumProcessDefinitionMapper {

    List<MiranumProcessDefinition> map(List<ProcessDefinition> list);

    MiranumProcessDefinition map(ProcessDefinition processDefinition);

}
