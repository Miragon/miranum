# Element Template Generator Plugin

## Usage

```xml
<build>
    <plugins>
        <plugin>
            <groupId>io.miragon.miranum</groupId>
            <artifactId>element-templates-generator-maven-plugin</artifactId>
            <configuration>
                <targetPlatform>${camunda.target.platform}</targetPlatform>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### Configuration

| Name                     |                | Description                                                      | Possible Values           | Default Value                                                    |
|--------------------------|----------------|------------------------------------------------------------------|---------------------------|------------------------------------------------------------------|
| `project`                | **(required)** | The Maven project.                                               |                           | ${project}                                                       |
| `targetPlatform`         | **(required)** | The target platform for which the templates should be generated. | `C7`, `C8`                | None                                                             |
| `outputDirectory`        | **(optional)** | The directory where the generated templates should be saved.     | Any valid path            | `${project.build.directory/generated-sources/element-templates}` |
| `inputValueNamingPolicy` | **(optional)** | The naming policy for the input value.                           | `EMPTY`, `ATTRIBUTE_NAME` | `EMPTY`                                                          |
| `clean`                  | **(optional)** | Clean the output directory before generating the templates.      | `true`, `false`           | `false`                                                          |

> :information_source: 
> **Note:** The `inputValueNamingPolicy` is only relevant for the generation of the input value. 
> The default value `EMPTY` will generate an empty input value. 
> The value `ATTRIBUTE_NAME` will generate an input value with the name of the attribute.
>  
> | `inputValueNamingPolicy` | Result                                                                                                                                                                                                                                                                                                                                  |
> |--------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
> | `EMPTY`                  | {<br>&nbsp;&nbsp;<code>"value" : "${}",</code><br>&nbsp;&nbsp;"label" : "Input: orderId",<br>&nbsp;&nbsp;"type" : "String",<br>&nbsp;&nbsp;"editable" : true,<br>&nbsp;&nbsp;"binding" : {<br>&nbsp;&nbsp;&nbsp;&nbsp;"type" : "camunda:inputParameter",<br>&nbsp;&nbsp;&nbsp;&nbsp;"name" : "orderId"<br>&nbsp;&nbsp;}<br>}<br>        |
> | `ATTRIBUTE_NAME`         | {<br>&nbsp;&nbsp;<code>"value" : "${orderId}",</code><br>&nbsp;&nbsp;"label" : "Input: orderId",<br>&nbsp;&nbsp;"type" : "String",<br>&nbsp;&nbsp;"editable" : true,<br>&nbsp;&nbsp;"binding" : {<br>&nbsp;&nbsp;&nbsp;&nbsp;"type" : "camunda:inputParameter",<br>&nbsp;&nbsp;&nbsp;&nbsp;"name" : "orderId"<br>&nbsp;&nbsp;}<br>}<br> |

### Annotations

It is **optional** to use annotations to provide additional information about the desired output.
1. `@ElementTemplate()`
2. `@ElementTemplateProperty()`

```java
import io.miragon.miranum.connect.elementtemplate.api.ElementTemplateProperty;

public class MiranumWorkerAdapter {
    @Worker(type = "orderProcess")
    @ElementTemplate(name = "Order Process", description = "Processes an incoming order.", version = 1)
    public OrderResult processOrder(OrderDto order) {
        // process order
    }
}

class OrderDto {
    @ElementTemplateProperty(type = "String", label = "Order ID", notEmpty = true, editable = false)
    private String orderId;
    @ElementTemplateProperty(type = "String", label = "Order Value", notEmpty = true, editable = false)
    private String orderValue;
}

class OrderResult {
    @ElementTemplateProperty(type = "String", label = "Delivery Date", notEmpty = true, editable = false)
    private String deliveryDate;
}
```

## Minimal Example

### Camunda 7

```java
public class MiranumWorkerAdapter {
    @Worker(type="orderProcess")
    public OrderResult processOrder(OrderDto order) {
        // process order
    }
}

class OrderDto {
    private String orderId;
    private String orderValue;
}

class OrderResult {
    private String deliveryDate;
}
```

```json
{
  "$schema" : "https://unpkg.com/@camunda/element-templates-json-schema@0.1.0/resources/schema.json",
  "name" : "orderProcess",
  "id" : "orderProcess",
  "appliesTo" : [ "bpmn:ServiceTask" ],
  "properties" : [ {
    "value" : "external",
    "label" : "Implementation Type",
    "type" : "String",
    "editable" : false,
    "binding" : {
      "type" : "property",
      "name" : "camunda:type"
    }
  }, {
    "value" : "orderProcess",
    "label" : "Topic",
    "type" : "String",
    "editable" : false,
    "binding" : {
      "type" : "property",
      "name" : "camunda:topic"
    }
  }, {
    "value" : "${}",
    "label" : "Input: orderId",
    "type" : "String",
    "editable" : true,
    "binding" : {
      "type" : "camunda:inputParameter",
      "name" : "orderId"
    }
  }, {
    "value" : "${}",
    "label" : "Input: orderValue",
    "type" : "String",
    "editable" : true,
    "binding" : {
      "type" : "camunda:inputParameter",
      "name" : "orderValue"
    }
  }, {
    "value" : "deliveryDate",
    "label" : "Output: deliveryDate",
    "type" : "String",
    "binding" : {
      "type" : "camunda:outputParameter",
      "source" : "${deliveryDate}"
    }
  } ]
}
```
