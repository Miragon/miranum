package io.miranum.platform.engine.processinstance.api.mapper;

import io.miranum.platform.engine.processinstance.api.transport.ServiceInstanceDetailTO;
import io.miranum.platform.engine.processinstance.api.transport.ServiceInstanceTO;
import io.miranum.platform.engine.processinstance.domain.model.ServiceInstance;
import io.miranum.platform.engine.processinstance.domain.model.ServiceInstanceDetail;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Map between {@link ServiceInstanceTO} and {@link ServiceInstance}
 *
 * @author externer.dl.horn
 */
@Mapper
public interface ServiceInstanceApiMapper {

    List<ServiceInstanceTO> map2TO(List<ServiceInstance> list);

    ServiceInstanceDetailTO map2TO(ServiceInstanceDetail instanceDetail);

}
