package io.miranum.platform.engine.processinstance.domain.mapper;

import io.miranum.platform.engine.processinstance.domain.model.ServiceInstance;
import io.miranum.platform.engine.processinstance.domain.model.ServiceInstanceDetail;
import io.miranum.platform.engine.processinstance.infrastructure.entity.ServiceInstanceEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Map to {@link ServiceInstance}
 */
@Mapper
public interface ServiceInstanceMapper {

    List<ServiceInstance> map2Model(List<ServiceInstanceEntity> list);

    ServiceInstanceDetail map2Detail(ServiceInstance obj);

    ServiceInstance map2Model(ServiceInstanceEntity obj);

    ServiceInstanceEntity map2Entity(ServiceInstance serviceInstance);

}
