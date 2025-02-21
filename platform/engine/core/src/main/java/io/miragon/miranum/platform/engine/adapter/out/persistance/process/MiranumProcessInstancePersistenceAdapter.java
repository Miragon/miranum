package io.miragon.miranum.platform.engine.adapter.out.persistance.process;

import io.miragon.miranum.platform.engine.adapter.out.persistance.process.jpa.MiranumProcessInstanceEntity;
import io.miragon.miranum.platform.engine.adapter.out.persistance.process.jpa.MiranumProcessInstanceRepository;
import io.miragon.miranum.platform.engine.adapter.out.persistance.process.jpa.ProcessInstanceAuthorizationEntity;
import io.miragon.miranum.platform.engine.adapter.out.persistance.process.jpa.ProcessInstanceAuthorizationRepository;
import io.miragon.miranum.platform.engine.adapter.out.persistance.process.mapper.MiranumProcessInstanceMapper;
import io.miragon.miranum.platform.engine.application.port.out.process.ProcessInstanceOutPort;
import io.miragon.miranum.platform.engine.domain.process.MiranumProcessInstance;
import io.miragon.miranum.platform.engine.domain.process.MiranumProcessInstanceWithData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class MiranumProcessInstancePersistenceAdapter implements ProcessInstanceOutPort {

    private final MiranumProcessInstanceRepository miranumProcessInstanceRepository;
    private final MiranumProcessInstanceMapper miranumProcessInstanceMapper;
    private final ProcessInstanceAuthorizationRepository processInstanceAuthorizationRepository;


    @Override
    public void save(MiranumProcessInstance processInstance) {
        miranumProcessInstanceRepository.save(miranumProcessInstanceMapper.map2Entity(processInstance));
    }


    @Override
    public List<MiranumProcessInstance> getAllByUser(final String userId) {
        return this.miranumProcessInstanceRepository.findAllByUserId(userId).stream()
                .map(miranumProcessInstanceMapper::map2Model)
                .toList();
    }

    @Override
    public void authorizeServiceInstance(String instanceId, String userId) {

        if (hasAccess(instanceId, userId)) {
            return;
        }

        final ProcessInstanceAuthorizationEntity entity = ProcessInstanceAuthorizationEntity.builder()
                .processInstanceId(instanceId)
                .userId(userId)
                .build();
        this.processInstanceAuthorizationRepository.save(entity);
    }

    @Override
    public MiranumProcessInstanceWithData getProcessInstanceWithData(String instanceId) {
        final MiranumProcessInstanceEntity entity = this.miranumProcessInstanceRepository.findById(instanceId).orElseThrow();
        return this.miranumProcessInstanceMapper.map2Detail(entity);
    }

    @Override
    public boolean hasAccess(final String instanceId, final String userId) {
        return this.processInstanceAuthorizationRepository.findByUserIdAndProcessInstanceId(userId, instanceId).isPresent();
    }

    @Override
    public Optional<MiranumProcessInstance> searchProcessInstanceById(String instanceId) {
        return this.miranumProcessInstanceRepository.findById(instanceId).map(miranumProcessInstanceMapper::map2Model);
    }

}
