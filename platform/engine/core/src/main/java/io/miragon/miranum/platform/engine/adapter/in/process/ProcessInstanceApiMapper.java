package io.miragon.miranum.platform.engine.adapter.in.process;

import io.miragon.miranum.platform.engine.domain.process.MiranumProcessInstance;
import io.miragon.miranum.platform.engine.domain.process.MiranumProcessInstanceWithData;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Map between {@link ProcessInstanceDto} and {@link MiranumProcessInstance}
 *
 * @author externer.dl.horn
 */
@Mapper
public interface ProcessInstanceApiMapper {

    List<ProcessInstanceDto> map2TO(List<MiranumProcessInstance> list);

    ProcessInstanceDto map2TO(MiranumProcessInstance obj);

    ProcessInstanceDetailDto map2TO(MiranumProcessInstanceWithData instanceDetail);

}
