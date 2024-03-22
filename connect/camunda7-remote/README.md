## Supported Use Cases

### Message

### Worker

#### Adjusting Retry Count for Camunda 7 Workers

This feature allows you to adjust the retry count for Camunda 7 workers. With the introduction of the `getRemainingRetries` method in `Camunda7WorkerAdapter`, developers can control the number of retries applied to an external task.

##### Usage

The `getRemainingRetries` method is used to determine the remaining number of retries for a task. It considers various sources to determine the retry count.

##### Priority Order

The `getRemainingRetries` method follows a specific priority order when determining the retry count:

1. **BPMN Retries** (Process Input Variables): If the BPMN definition of the task has specified retries and this information is available as input variables, the BPMN retries will be used.

2. **Overridden Default Value**: If no BPMN retries are present, an overridden default value can be used. This value can be set using an input variable (process variable).

3. **Default Value**: If neither BPMN retries nor an overridden default value is present, a default value of 3 will be used.

##### Changing the Retry Count

To adjust the retry count, you can define the corresponding input variable (`retries`) for the worker. This input variable can be set in the BPMN diagram and will be recognized by the `Camunda7WorkerAdapter`.

The `retries` input variable can contain any integer value. If the input variable is set, the retry count will be adjusted according to this value. Otherwise, the priority order described above will be applied to determine the retry count.

**Example:**

Suppose we want to set the retry count for a Camunda 7 worker to 5. To do this, we add an input variable `retries` in the BPMN diagram and set its value to 5.

```xml
<!-- BPMN Diagram -->
<camunda:inputParameter name="retries">5</camunda:inputParameter>
```

##### Configuring the Default Retry Count
You can configure the default retry count by using an application.properties property.
In the application.properties file, set the following property for Camunda 7 workers: `miranum.camunda7.worker.defaultRetries`.
This property allows you to set the default retry count for all Camunda 7 workers.


```properties
# application.properties
miranum.camunda7.worker.defaultRetries=3
```

By setting this property, the default retry count for Camunda 7 workers will be adjusted accordingly.