<div id="top"></div>

## Simple Example

*This simple example project demonstrates the use of miranum-core. Here we have created a fairly simple process that is automated using 
miranum-core. The project consists of three modules:*
- [simple-example_camunda-7](process-example-camunda-7)
- [simple-example_camunda-8](simple-example-camunda-8)
- [simple-example-core](process-example-core)

*The application and domain logic is implemented in the `simple-example-core` project. It contains the services that are called by a workflow engine.*
*The process is modelled using the open standard bpmn.*
*The following figure shows the process that is automated in the project:*

<div align="center">
   <img src="../../images/simple-example-bpmn.png" alt="simple-example-bpmn">
</div>

<p align="right">(<a href="#top">back to top</a>)</p>

### Getting Started

1. Run this commands to start a `camunda-7` workflow engine running inside a docker container:
    ```shell
    cd ../../stack/camunda-7/
    docker-compose up -d
    ```
   
2. Start the application [ExampleC7Application.java](process-example-camunda-7/src/main/java/io/miragon/miranum/integrations/example/c7/ExampleC7Application.java)
 
3. Open [message-c7.bpmn](process-example-camunda-7/src/main/resources/bpmn/message-c7.bpmn) in the `Camunda modeler`.
 
4. Deploy diagram

Now you can simply start the process instance and view the log in the application console.

<p align="right">(<a href="#top">back to top</a>)</p>
