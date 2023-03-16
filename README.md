![](https://img.shields.io/badge/Compatible%20with-Camunda%20Platform%207-blue)
![](https://img.shields.io/badge/Compatible%20with-Camunda%20Platform%208-blue)
![](https://img.shields.io/bower/l/bootstrap)
![](https://img.shields.io/github/issues/FlowSquad/miranum)

<div align="center">
    <a href="#">
        <img src="/images/logo_blau.png" alt="Logo" height="50">
    </a>
    <h3><a href="https://miranum.com/">Miranum Connect</a></h3>
    <p>
        <i>Simplifying the complexity of process automation.</i>
        <br />
        <a href="https://github.com/flowsquad/miranum/issues">Report Bug</a>
        ·
        <a href="https://github.com/flowsquad/miranum/issues">Request Feature</a>
    </p>
</div>

## About The Project
Miranum-Connect is a library which enables developers to build reusable and technology neutral connectors and integrations between products.
More information can be found in the [official documentation](https://miranum.com/docs/components/miranum-connect/intro-miranum-connect).

## Version Compatibility
| Miranum Connect Version | JDK  | Camunda Platform Versions | Compatible Spring Boot versions  |
|-------------------------|------|---------------------------|----------------------------------|
| >=0.1.0                 | > 11 | >=8.0, >=7.15             | >= 2.7.6, 3.0.x                  |

## Get Started
To use the Java Miranum-Connect library, declare the following Maven dependency in your project:

```xml
<dependency>
 <groupId>io.miragon.miranum</groupId>
 <artifactId>connect</artifactId>
 <version>0.1.0-SNAPSHOT</version>
</dependency>
```

This library can be used only in combination with SpringBoot. Miranum-Connect is currently available as **snapshot-release** in maven-central. 
You can import all Java moduls as dependencies. Make sure to use the latest version.

To pull dependencies from the maven snapshot repo it needs to be part of the settings.xml for your user profile.
<details>
  <summary><code>~/.m2/settings.xml</code></summary>

   ``` xml
   <?xml version="1.0" encoding="UTF-8"?>
   <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">
      <localRepository />
      <interactiveMode />
      <offline />
      <pluginGroups />
      <servers />
      <mirrors />
      <proxies />
      <profiles>
         <profile>
            <id>allow-snapshots</id>
            <activation>
               <activeByDefault>true</activeByDefault>
            </activation>
            <repositories>
               <repository>
                  <id>snapshots-repo</id>
                  <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
                  <releases>
                     <enabled>false</enabled>
                  </releases>
                  <snapshots>
                     <enabled>true</enabled>
                  </snapshots>
               </repository>
            </repositories>
         </profile>
      </profiles>
      <activeProfiles />
   </settings>
   ```
</details>
<p align="right">(<a href="#top">back to top</a>)</p>

### Miranum-Worker
A Miranum-Worker connects to the process engine (e.g. Camunda Platform 7 or 8) and fetches tasks of a certain type. With 
this functionality, our integration perform various actions on such a task occurring in a process instance. 
After having worked and completed a task via a worker, the process engine continues to the next step.

To use the worker make sure to add the `worker-api` dependency to your project and import it to your java-class. 
```java
import io.miragon.miranum.connect.worker.api.Worker;
```

A method which should acts as a worker needs to have the `@Worker` annotation. You are required to set a type which is used 
as reference to specify which job worker request the respective service task job.
Your method can be of any return type. Set void if you do not want to store anything back to the scope of the process engine.
If you have an object in return it will be stored in a local-context in the process instance. If you want to use it globally
make sure to define the input/output mappings in your process.

```java
@Worker(type = "my-type") 
public void doSomething(DoSomethingCommand doSomethingCommand) {
    doSomethingUseCase.doSomething(doSomethingCommand);
}
```


### Miranum-Process
Miranum-Process connects to the process engine (e.g. Camunda Platform 7 or 8) and enables users to start a process from 
code. Once you have started the process, you can use worker to step through it.

To use Miranum-Process make sure to add the `process-api` dependency to your project and import it to your java-class.
```java
import io.miragon.miranum.connect.process.api.StartProcessCommand;
import io.miragon.miranum.connect.process.impl.StartProcessPort;
```

To implement a method which starts a process instance from our code we use a REST-Controller in this example.  You are not 
limited to use REST and can also rely on gRPC and other API mechanisms.
For the start of the process instance, we need the `StartProcessPort` from our Miranum-Process implementation.
In the method `triggerProcessStart` we call the `startProcessPort` to start the actual process and submit a `StartProcessCommand`.
The command contains the key of the process which we want to start as well as possible variables which we want to
initialize on startup of the process instance.

```java
@RestController
@RequestMapping("api/process")
@AllArgsConstructor
public class StartProcessController {


    private final StartProcessPort startProcessPort;

    @PutMapping("/start")
    public ResponseEntity<Void> triggerProcessStart(@RequestBody final StartProcessRequestDto startProcessRequestDto) {
        this.startProcessPort.startProcess(new StartProcessCommand(startProcessRequestDto.getProcessKey(), startProcessRequestDto.getVariables()));
        return ResponseEntity.ok().build();
    }
}
```


### Miranum-Message
Miranum-Message connects to the process engine (e.g. Camunda Platform 7 or 8) and enables users to correlate messages with 
process instance.

To use Miranum-Message make sure to add the `message-api` dependency to your project and import it to your java-class.
```java
import io.miragon.miranum.connect.message.api.CorrelateMessageCommand;
import io.miragon.miranum.connect.message.api.MessageApi;
```
To implement a method which handles the message correlation initialise
the Message-API. Having done so, we can create a new method `correlateMessage` which gets the CorrelateMessageCommand as an input.
Using `messageApi.correlateMessage` I can correlate the message with my process, by specifying the message name, correlation key
and the variables which should be sent back to the process.
In case a message correlation is not possible, an exception is thrown.

```java
private final MessageApi messageApi;
    
public void correlateMessage(CorrelateMessageCommand message) {
    log.info("Received message: " + message);
    messageApi.correlateMessage(new CorrelateMessageCommand(message.getName(), message.getKey(), Map.of(message.getVariables())));
    log.info(message + " successfully correlated")
}
```

## Contributing
Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any
contributions you make are **greatly appreciated**.
To collect, prioritise and work on issues in a collaborative fashion we have established some [contribution guidelines](https://miranum.com/docs/components/contributing) as well as a [GitHub project](https://github.com/orgs/FlowSquad/projects/9).
Please make sure to read through these guidelines before starting with your contribution.

### PRs for every change
All changes have to be done in a separate Branch. As soon as the changes are done please open a PR. A GitHub Action runs
with every commit to a Branch and checks if the documentation can be built. If you create a new branch make sure to name it according
to what it does (e.g. feat/xyz or fix/xyz). Please use semantic commit messages as described in [here](https://gist.github.com/joshbuchea/6f47e86d2510bce28f8e7f42ae84c716).

### Code reviews
We are using simple emoji's to help convey intention and added meaning in code review comments. A little bit of emoji can 
go a long way when it comes to code reviews and make giving and receiving code review a little bit more enjoyable. 
We are following this [guide by erikthedeveloper](https://github.com/erikthedeveloper/code-review-emoji-guide).

## Code of Conduct
This project adheres to the [Contributor Covenant Code of Conduct](./CODE_OF_CONDUCT.md). By participating, you are expected to uphold this code. 
Please report unacceptable behavior to info@miragon.io.

## License
Distributed under the MIT License. See `LICENSE` file for more information.
<p align="right">(<a href="#top">back to top</a>)</p>
