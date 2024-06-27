# Element Template Generator Plugin

## Usage

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <artifactId>miranum-inquiry-example</artifactId>

    <parent>
        <groupId>io.miragon.miranum</groupId>
        <artifactId>miranum-examples</artifactId>
        <version>${revision}</version>
    </parent>

    <properties>
        <revision>1.0.0</revision>
        <spring-boot.version>3.1.9</spring-boot.version>
        <camunda7.version>7.20.0</camunda7.version>
        <miranum-connect.version>0.7.1-SNAPSHOT</miranum-connect.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <!-- Import dependency management from Spring Boot -->
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <!-- Spring Boot -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- Camunda Client -->
        <dependency>
            <groupId>org.camunda.community</groupId>
            <artifactId>camunda-engine-rest-client-complete-springboot-starter</artifactId>
            <version>${camunda7.version}</version>
        </dependency>
        
        <!-- miranum -->
        <dependency>
            <groupId>io.miragon.miranum.connect</groupId>
            <artifactId>connect-camunda7-remote-all</artifactId>
            <version>${miranum-connect.version}</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>io.miragon.miranum.connect</groupId>
                <artifactId>element-templates-generator-maven-plugin</artifactId>
                <version>${miranum-connect.version}</version>
                <configuration>
                    <targetPlatform>C7</targetPlatform>
                    <inputValueNamingPolicy>ATTRIBUTE_NAME</inputValueNamingPolicy>
                    <clean>true</clean>
                    <outputDirectory>src/main/resources/processes/element-templates</outputDirectory>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>generate</goal>
                        </goals>
                        <phase>process-classes</phase>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

</project>

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
