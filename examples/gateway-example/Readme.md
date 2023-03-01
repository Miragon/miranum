# Gateway-Example

The started GatewayC7Application will be available at `localhost:5050`.

Test to create a process instance via cURL:
```
curl --location --request POST 'http://localhost:5050/cloudevents/' \
--header 'Content-Type: application/cloudevents+json' \
--data-raw '{
  "specversion" : "1.0",
  "id": "f5276fe8-a26d-4e86-862b-525f94600b57",
  "source": "//my.fancy.system/projects/my-project/topics/my-topic",
  "type": "test",
  "datacontenttype" : "application/json",
  "data": {
    "bpmnProcessId": "test.bpmn"
  }
}'
```
