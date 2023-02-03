<div id="top"></div>

### Getting Started

1. Run this commands to start a `camunda-7` workflow engine running inside a docker container:
    ```shell
    cd ../../stack/camunda-7/
    docker-compose up -d
    ```
   
2. Start the application [ExampleC7Application.java](simple-example-camunda-7/src/main/java/io/miragon/miranum/integrations/example/c7/ExampleC7Application.java)
 
3. Open [message-c7.bpmn](simple-example-camunda-7/src/main/resources/bpmn/message-c7.bpmn) in the `Camunda modeler`.
 
4. Deploy diagram

Now you can simply start the process instance and view the log in the application console.

<p align="right">(<a href="#top">back to top</a>)</p>
