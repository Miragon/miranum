package io.miragon.miranum.platform.engine.application.port.out.process;


import io.miragon.miranum.platform.engine.domain.process.ProcessConfig;

/**
 * Port to access the config.
 */
public interface ProcessConfigPort {


    ProcessConfig getByRef(String schemaId);
}
