package io.miranum.platform.engine.processdefinition.api.mapper;

import io.miranum.platform.engine.processdefinition.api.transport.ServiceDefinitionDetailTO;
import io.miranum.platform.engine.processdefinition.api.transport.ServiceDefinitionTO;
import io.miranum.platform.engine.processdefinition.domain.model.ServiceDefinition;
import io.miranum.platform.engine.processdefinition.domain.model.ServiceDefinitionDetail;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper()
public interface ServiceDefinitionApiMapper {

    List<ServiceDefinitionTO> map2TO(List<ServiceDefinition> list);

    ServiceDefinitionDetailTO map2TO(ServiceDefinitionDetail obj);

}
