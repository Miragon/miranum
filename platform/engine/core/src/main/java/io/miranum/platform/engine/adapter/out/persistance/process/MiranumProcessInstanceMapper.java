package io.miranum.platform.engine.adapter.out.persistance.process;

import io.miranum.platform.engine.domain.process.MiranumProcessInstance;
import io.miranum.platform.engine.domain.process.MiranumProcessInstanceWithData;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Map to {@link MiranumProcessInstance}
 */
@Mapper
public interface MiranumProcessInstanceMapper {

    List<MiranumProcessInstance> map2Model(List<MiranumProcessInstanceEntity> list);

    MiranumProcessInstanceWithData map2Detail(MiranumProcessInstanceEntity obj);

    MiranumProcessInstance map2Model(MiranumProcessInstanceEntity obj);

    MiranumProcessInstanceEntity map2Entity(MiranumProcessInstance serviceInstance);

}
