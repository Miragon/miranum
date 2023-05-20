package io.miranum.platform.engine;

import io.holunda.polyflow.datapool.core.EnablePolyflowDataPool;
import io.holunda.polyflow.taskpool.core.EnablePolyflowTaskPool;
import io.miranum.platform.tasklist.EnableTasklist;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableTasklist
@EnablePolyflowTaskPool
@EnablePolyflowDataPool
@SpringBootApplication
public class EngineTasklistApplication {

    public static void main(String[] args) {
        SpringApplication.run(EngineTasklistApplication.class, args);
    }
}
