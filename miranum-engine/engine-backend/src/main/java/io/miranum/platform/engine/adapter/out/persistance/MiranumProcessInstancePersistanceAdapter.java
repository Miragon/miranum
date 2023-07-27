package io.miranum.platform.engine.adapter.out.persistance;

import io.miranum.platform.engine.application.port.out.process.MiranumProcessInstancePort;
import io.miranum.platform.engine.domain.process.MiranumProcessInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class MiranumProcessInstancePersistanceAdapter implements MiranumProcessInstancePort {

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

    private boolean hasAccess(final String instanceId, final String userId) {
        return this.processInstanceAuthorizationRepository.findByUserIdAndProcessInstanceId(userId, instanceId).isPresent();
    }
}
