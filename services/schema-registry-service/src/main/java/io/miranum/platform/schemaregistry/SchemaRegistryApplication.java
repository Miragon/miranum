package io.miranum.platform.schemaregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EntityScan(basePackages = "io.miragon.miranum.connect.json.registry.adapter")
//@ComponentScan(basePackages = {
//        "io.miragon.miranum.connect.json.registry.adapter",
//        "io.miragon.miranum.connect.json.registry.application",
//        "io.miranum.platform.schemaregistry"})
//@EnableJpaRepositories(basePackages = "io.miragon.miranum.connect.json.registry.adapter")
public class SchemaRegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(SchemaRegistryApplication.class, args);
    }
}
