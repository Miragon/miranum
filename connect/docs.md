# Miranum Connect

Miranum Connect is a powerful, engine-neutral framework designed to facilitate integrations and streamline process automation. It is built using the hexagonal architecture, ensuring that your domain logic remains independent of any third-party systems or technical environments.

- **Reusable Integrations:** Build connectors that are not tied to specific vendors.
- **Technology Neutral:** Easily migrate infrastructure components without impacting business logic.
- **Hexagonal Architecture:** Promotes clean code, testability, and maintainability.

## ⚠️ Deprecation Notice

Miranum Connect is no longer under active development. We recommend considering bpm-crafters [process-engine-worker](https://github.com/bpm-crafters/process-engine-worker) for new projects.

---

## Quickstart Guide

### What You Will Learn

- How to create engine-neutral Workers
- How to correlate messages
- How to start processes

### Getting Started

Miranum Connect provides a set of core features to help you automate and integrate with process engines like Camunda 7 and 8.

- [Creating Workers](#creating-workers)
- [Correlating Messages](#correlating-messages)
- [Starting Processes](#starting-processes)

---

## Creating Workers

Workers are essential components that bridge your process engine and your tasks, enabling streamlined process automation.

### Dependencies

Add the following Maven dependencies to your project:

```xml
<dependency>
  <groupId>io.miragon.miranum</groupId>
  <artifactId>worker-api</artifactId>
  <version>0.14.0</version>
</dependency>
<!-- For Camunda 7 -->
<dependency>
  <groupId>io.miragon.miranum</groupId>
  <artifactId>worker-adapter-c7</artifactId>
  <version>0.14.0</version>
</dependency>
```

### Implementation

Workers are typically implemented in the adapter.in package, following the hexagonal architecture

```java
import io.miragon.miranum.connect.worker.api.Worker;

@Worker(type = "my-type")
public DoSomethingResult doSomething(DoSomethingCommand doSomethingCommand) {
    return doSomethingUseCase.doSomething(doSomethingCommand);
}
```

- The `@Worker` annotation marks the method as a worker for a specific task type.
- The method can return `void` or an object. Returned objects are stored as local variables in the process instance.

### Commands and Use Cases

Commands and use cases are typically created in the `application.port.in` package.

```java
public class DoSomethingService implements DoSomethingUseCase {
    private final DomainObject domainObject;

    @Override
    public void doSomething(DoSomethingCommand doSomethingCommand) {
        domainObject.doSomething(doSomethingCommand.getSomething());
    }
}
```

### Return Variables

To make returned variables globally available in the process, use input/output mappings in your BPMN model.


## Correlating Messages

Message correlation allows your application to interact asynchronously with running workflows.

### Dependencies

```xml
<dependency>
  <groupId>io.miragon.miranum</groupId>
  <artifactId>message-api</artifactId>
  <version>0.14.0</version>
</dependency>
<!-- For Camunda 7 -->
<dependency>
  <groupId>io.miragon.miranum</groupId>
  <artifactId>message-adapter-c7</artifactId>
  <version>0.14.0</version>
</dependency>
```

### Implementation

Implement message correlation in the `adapter.in` package.

```java
import io.miragon.miranum.connect.message.api.CorrelateMessageCommand;
import io.miragon.miranum.connect.message.api.MessageApi;

@RequiredArgsConstructor
public class MessageCorrelationService {
    private final MessageApi messageApi;

    public void correlateMessage(CorrelateMessageCommand message) {
        messageApi.correlateMessage(new CorrelateMessageCommand(
            message.getName(),
            message.getKey(),
            Map.of(message.getVariables())
        ));
    }
}
```

If correlation is not possible, an exception is thrown.

## Starting Processes

Miranum Process enables you to start a process instance from your code.

### Dependencies

```xml
<dependency>
  <groupId>io.miragon.miranum</groupId>
  <artifactId>process-api</artifactId>
  <version>0.14.0</version>
</dependency>
<!-- For Camunda 7 -->
<dependency>
  <groupId>io.miragon.miranum</groupId>
  <artifactId>process-adapter-c7</artifactId>
  <version>0.14.0</version>
</dependency>
```

### Implementation

Implement process starting logic in the `adapter.in` package.

```java
import io.miragon.miranum.connect.process.api.StartProcessCommand;
import io.miragon.miranum.connect.process.impl.StartProcessPort;

@RestController
@RequestMapping("api/process")
@AllArgsConstructor
public class StartProcessController {
    private final StartProcessPort startProcessPort;

    @PutMapping("/start")
    public ResponseEntity<Void> triggerProcessStart(@RequestBody final StartProcessRequestDto startProcessRequestDto) {
        this.startProcessPort.startProcess(
            new StartProcessCommand(
                startProcessRequestDto.getProcessKey(),
                startProcessRequestDto.getVariables()
            )
        );
        return ResponseEntity.ok().build();
    }
}
```

## Generating Camunda Element Templates

Miranum supports generating element templates for Camunda 7 and 8.

### Maven Plugin

Add the plugin to your pom.xml:

```xml
<plugin>
    <groupId>io.miragon.miranum</groupId>
    <artifactId>element-templates-generator-maven-plugin</artifactId>
    <version>${project.version}</version>
    <configuration>
        <targetPlatforms>camunda7</targetPlatforms>
    </configuration>
</plugin>
```

### API Dependency

```xml
<dependency>
    <groupId>io.miragon.miranum</groupId>
    <artifactId>element-template-api</artifactId>
    <version>${project.version}</version>
    <scope>compile</scope>
</dependency>
```

### Annotate Worker

```java
@Worker(type = "doSomething")
@ElementTemplate(
    name = "Do Something",
    description = "This is a description"
)
public void doSomething(DoSomethingCommand doSomethingCommand) {}
```

### Customize Template Properties

```java
class DoSomethingCommand {
    @ElementTemplateProperty(name = "Variable A", type = "String", required = true)
    private String a;

    @ElementTemplateProperty(name = "Variable B", type = "String", required = true)
    private String b;
}
```

Generated templates are stored in `target/classes/element-templates`.
