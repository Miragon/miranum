package io.miranum.platform.engine.adapter.in.schemaregistry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.miragon.miranum.connect.json.registry.application.ports.in.SaveSchemaInCommand;
import io.miragon.miranum.connect.json.registry.application.ports.in.SaveSchemaUseCase;
import io.miragon.miranum.deploymentreceiver.application.DeploymentFailedException;
import io.miragon.miranum.deploymentreceiver.application.ports.out.MiranumDeploymentReceiver;
import io.miragon.miranum.deploymentreceiver.domain.Deployment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchemaDeploymentReceiver implements MiranumDeploymentReceiver {

    private final SaveSchemaUseCase saveSchemaUseCase;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void deploy(final Deployment deployment, final List<String> tags) {
        try {
            final String namespace = deployment.getNamespace();
            final JsonNode jsonNode = this.objectMapper.readTree(deployment.getFile());
            final String schemaRef = Optional.of(jsonNode.get("key")).map(JsonNode::asText)
                    .orElseThrow(() -> new DeploymentFailedException("No key found in schema " + jsonNode.get("key")));

            switch (deployment.getType().toLowerCase()) {
                case "form":
                    final JsonNode schema = jsonNode.get("schema");
                    this.saveSchemaUseCase.saveSchema(new SaveSchemaInCommand(namespace, schemaRef, tags, schema));
                    break;
                case "config":
                    this.saveSchemaUseCase.saveSchema(new SaveSchemaInCommand(namespace, schemaRef, tags, jsonNode));
                    break;
                default:
                    log.info("Ignoring deployment of type {}", deployment.getType().toLowerCase());
            }
        } catch (final IOException e) {
            log.error("Could not parse schema {}", deployment.getFilename(), e);
            throw new DeploymentFailedException("Could not parse schema " + deployment.getFilename());
        }
    }

}
