package io.miragon.miranum.platform.deployment.adapter.out.engine;

import io.miragon.miranum.platform.deployment.application.ports.out.engine.ArtifactDeploymentOutPort;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.dmn.Dmn;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DeploymentAdapter implements ArtifactDeploymentOutPort {

    private final RepositoryService repositoryService;

    @Override
    public void deployBpmn(String file, String filename, String namespace, List<String> tags) {
        final BpmnModelInstance model = Bpmn.readModelFromStream(new ByteArrayInputStream(file.getBytes()));
        Bpmn.validateModel(model);
        this.repositoryService.createDeployment()
                .addModelInstance(filename, model)
                .enableDuplicateFiltering(true)
                .name(filename)
                .deploy();
    }

    @Override
    public void deployDmn(String file, String filename, String namespace, List<String> tags) {
        final DmnModelInstance model = Dmn.readModelFromStream(new ByteArrayInputStream(file.getBytes()));
        Dmn.validateModel(model);
        this.repositoryService.createDeployment()
                .addModelInstance(filename, model)
                .enableDuplicateFiltering(true)
                .name(filename)
                .deploy();
    }
}
