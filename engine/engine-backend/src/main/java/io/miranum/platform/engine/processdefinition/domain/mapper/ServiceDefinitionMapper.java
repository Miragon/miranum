package io.miranum.platform.engine.processdefinition.domain.mapper;

import io.miranum.platform.engine.processdefinition.domain.model.ServiceDefinition;
import io.miranum.platform.engine.processdefinition.domain.model.ServiceDefinitionDetail;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Map between {@link ServiceDefinition} and {@link ProcessDefinition}
 */
@Mapper
public interface ServiceDefinitionMapper {

    List<ServiceDefinition> map(List<ProcessDefinition> list);

    ServiceDefinitionDetail map(ProcessDefinition processDefinition);

}
