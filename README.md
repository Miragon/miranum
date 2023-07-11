<div id="top"></div>

<!-- PROJECT SHIELDS -->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![C7][c7-shield]][c7-url]
[![C8][c8-shield]][c8-url]
<!-- END OF PROJECT SHIELDS -->

<!-- PROJECT LOGO -->
<br />
<div align="center">
    <a href="#">
        <img src="/images/miranum_logo.png" alt="Logo" height="180">
    </a>
    <h3><a href="https://miranum.com/">Miranum Connect</a></h3>
    <p>
        <i>Simplifying the complexity of process automation.</i>
        <br />
        <a href="https://github.com/FlowSquad/miranum-connect/issues">Report Bug</a>
        ·
        <a href="https://github.com/FlowSquad/miranum-connect/pulls">Request Feature</a>
    </p>
</div>

## About The Project
Miranum-Connect is a library which enables developers to build reusable and technology neutral connectors and integrations between products.
More information can be found in the [official documentation](https://miranum.com/docs/components/miranum-connect/intro-miranum-connect).

Content of this readme:
* [Version Compatibility](#version-compatibility)
* [Miranum-Worker](#miranum-worker)
* [Miranum-Process](#miranum-process)
* [Miranum-Message](#miranum-message)
* [Contribution](#contributing)
* [Code of Conduct](#code-of-conduct)
* [License](#license)

## Version Compatibility
| Miranum Connect Version | JDK  | Camunda Platform Versions | Compatible Spring Boot versions  |
|-------------------------|------|---------------------------|----------------------------------|
| >=0.1.0                 | > 11 | >=8.0, >=7.15             | >= 2.7.6, 3.0.x                  |

## Get Started
To use the Java Miranum-Connect library, declare the following Maven dependency in your project. As artifact ID you need to 
choose one of the following modules: worker-api, message-api, process-api, json-api, task-api.

```xml
<dependency>
 <groupId>io.miragon.miranum</groupId>
 <artifactId>{worker-api, message-api, process-api, json-api, task-api}</artifactId>
 <version>0.2.0</version>
</dependency>
```

This library can be used only in combination with SpringBoot.
<p align="right">(<a href="#top">back to top</a>)</p>

For fully executable examples, take a look at our [miranum-consulting repository](https://github.com/FlowSquad/miranum-consulting). 
Below, only small non-executable snippets are shown.

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
Worker feature an adjustable retry count. They are set through the BPMN model in Camunda Platform 8. Camunda 7 worker have a 
default of 3. This can be configured as described in the [C7 specific readme](./connect/connect-c7/README.md).
<p align="right">(<a href="#top">back to top</a>)</p>


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
<p align="right">(<a href="#top">back to top</a>)</p>


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
<p align="right">(<a href="#top">back to top</a>)</p>


## Contributing
Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any
contributions you make are **greatly appreciated**.
To collect, prioritise and work on issues in a collaborative fashion we have established some [contribution guidelines](https://miranum.com/docs/components/contributing) as well as a [GitHub project](https://github.com/orgs/FlowSquad/projects/9).
Please make sure to read through these guidelines before starting with your contribution.

### PRs for every change
All changes have to be done in a separate Branch or Fork (if you are an external contributor). As soon as the changes are 
done please open a PR. A GitHub Action runs with every commit to a Branch and checks if the documentation can be built.
If you create a new branch make sure to name it according to what it does (e.g. feat/xyz or fix/xyz). Please use semantic 
commit messages as described in [here](https://gist.github.com/joshbuchea/6f47e86d2510bce28f8e7f42ae84c716).

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


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/FlowSquad/miranum-connect.svg?style=for-the-badge
[contributors-url]: https://github.com/FlowSquad/miranum-connect/graphs/contributors

[forks-shield]: https://img.shields.io/github/forks/FlowSquad/miragon-connect.svg?style=for-the-badge
[forks-url]: https://github.com/FlowSquad/miranum-connect/network/members

[stars-shield]: https://img.shields.io/github/stars/FlowSquad/miragon-connect.svg?style=for-the-badge
[stars-url]: https://github.com/FlowSquad/miranum-connect/stargazers

[issues-shield]: https://img.shields.io/github/issues/FlowSquad/miranum-connect.svg?style=for-the-badge
[issues-url]: https://github.com/FlowSquad/miranum-connect/issues

[license-shield]: https://img.shields.io/github/license/FlowSquad/miranum-connect.svg?style=for-the-badge
[license-url]: https://github.com/FlowSquad/miranum-connect/blob/main/LICENSE

[c7-shield]: https://img.shields.io/badge/Compatible%20with-Camunda%20Platform%207-blue.svg?style=for-the-badge
[c7-url]: https://camunda.com/de/platform-7/

[c8-shield]: https://img.shields.io/badge/Compatible%20with-Camunda%20Platform%208-blue?style=for-the-badge
[c8-url]: https://camunda.com/de/platform/

