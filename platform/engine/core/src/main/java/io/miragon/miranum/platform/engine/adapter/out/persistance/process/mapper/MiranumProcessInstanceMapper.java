package io.miragon.miranum.platform.engine.adapter.out.persistance.process.mapper;

import io.miragon.miranum.platform.engine.adapter.out.persistance.process.jpa.MiranumProcessInstanceEntity;
import io.miragon.miranum.platform.engine.domain.process.MiranumProcessInstance;
import io.miragon.miranum.platform.engine.domain.process.MiranumProcessInstanceWithData;
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
