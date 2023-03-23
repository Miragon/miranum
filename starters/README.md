This is a collection of preconfigured ready-to-run applications which are useful for
the development and testing of library components.

For complete integration examples have a look at the [Miranum Consulting Repository](https://github.com/FlowSquad/miranum-consulting).

## Spring Boot Starters

### spring-examples

This directory contains implementation examples that extent miranum functionality for Spring.

### spring-web-c7

This starter is pre-configured with the following components:

* Camunda-7 adapter
* Message adapter for camunda-7
* CloudEvents adapter for spring-web
* CloudEvents adapter for messages

The started Spring Boot Web Application will be available at `localhost:5050`.

Test to send a message via cURL:

```
curl --location --request POST 'http://localhost:5050/cloudevents/' \
--header 'Content-Type: application/cloudevents+json' \
--data-raw '{
  "specversion" : "1.0",
  "id": "f5276fe8-a26d-4e86-862b-525f94600b57",
  "source": "//my.fancy.system/projects/my-project/topics/my-topic",
  "type": "io.miranum.bpmn.command.v1.PublishMessageRequest",
  "datacontenttype" : "application/json",
  "data": {
    "name": "start-message",
    "correlationKey": "test",
    "timeToLive": 2000,
    "messageId": "xyz",
    "variables": "{\"a\": 2}"
  }
}'
```

