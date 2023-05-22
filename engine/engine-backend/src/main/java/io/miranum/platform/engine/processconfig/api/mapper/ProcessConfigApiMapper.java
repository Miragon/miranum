package io.miranum.platform.engine.processconfig.api.mapper;

import io.miranum.platform.engine.processconfig.api.transport.ProcessConfigTO;
import io.miranum.platform.engine.processconfig.domain.model.ProcessConfig;
import org.mapstruct.Mapper;

/**
 * Map between {@link ProcessConfigTO} and {@link ProcessConfig}
 *
 * @author externer.dl.horn
 */
@Mapper
public interface ProcessConfigApiMapper {

    ProcessConfig map(ProcessConfigTO obj);

    ProcessConfigTO map2TO(ProcessConfig obj);

}
