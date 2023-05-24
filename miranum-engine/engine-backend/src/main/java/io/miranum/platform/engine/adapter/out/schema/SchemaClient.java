package io.miranum.platform.engine.adapter.out.schema;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(
        name = "${feign.client.config.jsonschema.name:engine-schema}",
        url = "${feign.client.config.engine-schema.url:${feign.client.config.default.url:http://localhost:8080/schema-registry}}"
)
public interface SchemaClient {

    @RequestMapping(value = "/schema/{bundle}/{ref}/{tag}", method = {RequestMethod.GET}, produces = {"application/json"})
    Map<String, Object> getSchemaById(@PathVariable("bundle") String bundle, @PathVariable("ref") String ref, @PathVariable("tag") String tag);
}
