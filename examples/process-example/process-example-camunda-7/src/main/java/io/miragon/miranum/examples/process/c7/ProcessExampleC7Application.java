package io.miragon.miranum.examples.process.c7;

import io.miragon.miranum.examples.process.ProcessExampleConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ProcessExampleConfiguration.class)
public class ProcessExampleC7Application {

    public static void main(final String[] args) {
        SpringApplication.run(ProcessExampleC7Application.class, args);
    }

}
