package io.miranum.platform.engine.adapter.out.engine;

import io.miranum.platform.engine.domain.process.MiranumProcessDefinition;
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
