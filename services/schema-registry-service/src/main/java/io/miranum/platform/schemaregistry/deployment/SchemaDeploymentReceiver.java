package io.miranum.platform.schemaregistry.deployment;

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
        // process configs and forms are both json schemas and saved the same way
        if (deployment.getType().equalsIgnoreCase("form") || deployment.getType().equalsIgnoreCase("config")) {
            try {
                final JsonNode jsonSchema = this.objectMapper.readTree(deployment.getFile());
                this.deployJsonSchema(jsonSchema, deployment.getNamespace(), tags);
                log.info("Deployed json schema {} of type {}", deployment.getFilename(), deployment.getType());
            } catch (final IOException e) {
                log.error("Could not parse schema {}", deployment.getFilename(), e);
                throw new DeploymentFailedException("Could not parse schema " + deployment.getFilename());
            }
        }
    }

    private void deployJsonSchema(final JsonNode schema, final String namespace, final List<String> tags) {
        final String schemaRef = Optional.of(schema.get("key")).map(JsonNode::asText)
                .orElseThrow(() -> new DeploymentFailedException("No key found in schema " + schema.get("key")));
        final SaveSchemaInCommand saveSchemaInCommand = new SaveSchemaInCommand(namespace, schemaRef, tags, schema);
        this.saveSchemaUseCase.saveSchema(saveSchemaInCommand);
    }
}
