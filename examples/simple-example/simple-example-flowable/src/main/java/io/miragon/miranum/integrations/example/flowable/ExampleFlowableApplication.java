package io.miragon.miranum.integrations.example.flowable;

import io.miragon.miranum.integrations.example.ExampleConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Import(ExampleConfiguration.class)
@AllArgsConstructor
@Slf4j
public class ExampleFlowableApplication {

    public static void main(final String[] args) {
        var context = SpringApplication.run(ExampleFlowableApplication.class, args);

        var repositoryService = context.getBean(RepositoryService.class);
        var runtimeService = context.getBean(RuntimeService.class);

        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("bpmn/message-flowable.bpmn20.xml")
                .deploy();

        log.info("Deployed: {}", deployment.getId());

        Map<String, Object> variables = new HashMap<>();
        variables.put("content", "myMessage");
        variables.put("key", "myKey");

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("message-flowable", variables);

        log.info("Process \"{}\" started", processInstance.getId());
    }

}