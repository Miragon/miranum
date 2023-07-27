package io.miranum.platform.engine.processinstance.api.mapper;

import io.miranum.platform.engine.domain.process.MiranumProcessInstance;
import io.miranum.platform.engine.processinstance.api.transport.ServiceInstanceDetailTO;
import io.miranum.platform.engine.processinstance.api.transport.ServiceInstanceTO;
import io.miranum.platform.engine.processinstance.domain.model.ServiceInstanceWithData;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Map between {@link ServiceInstanceTO} and {@link MiranumProcessInstance}
 *
 * @author externer.dl.horn
 */
@Mapper
public interface ServiceInstanceApiMapper {

    List<ServiceInstanceTO> map2TO(List<MiranumProcessInstance> list);

    ServiceInstanceDetailTO map2TO(ServiceInstanceWithData instanceDetail);

}
