package io.miragon.miranum.platform.engine.adapter.in.process.mapper;

import io.miragon.miranum.platform.engine.adapter.in.process.dto.ProcessDefinitionDto;
import io.miragon.miranum.platform.engine.domain.process.MiranumProcessDefinition;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper()
public interface ProcessDefinitionApiMapper {

    List<ProcessDefinitionDto> map2TO(List<MiranumProcessDefinition> list);

    ProcessDefinitionDto map2TO(MiranumProcessDefinition obj);


}
