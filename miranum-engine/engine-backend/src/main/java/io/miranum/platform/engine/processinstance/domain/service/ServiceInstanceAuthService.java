package io.miranum.platform.engine.processinstance.domain.service;

import io.miranum.platform.engine.processinstance.infrastructure.entity.ServiceInstanceAuthorizationEntity;
import io.miranum.platform.engine.processinstance.infrastructure.repository.ProcessInstanceAuthorizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service to handle Process Instance Authorizations.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Service("processInstanceAuthService")
@RequiredArgsConstructor
public class ServiceInstanceAuthService {

    private final ProcessInstanceAuthorizationRepository processInstanceAuthorizationRepository;

    /**
     * Get all authorized process instances for a user
     *
     * @param userId Id of the user
     * @return all process instances
     */
    public List<String> getAllServiceInstanceIdsByUser(final String userId) {
        return this.processInstanceAuthorizationRepository.findAllByUserId(userId).stream()
                .map(ServiceInstanceAuthorizationEntity::getProcessInstanceId)
                .collect(Collectors.toList());
    }

    /**
     * Create an authorization for a user
     *
     * @param processInstanceId Id of the process isntance
     * @param userId            Id of the user
     */
    public void createAuthorization(final String processInstanceId, final String userId) {

        if (hasAccess(processInstanceId, userId)) {
            return;
        }

        final ServiceInstanceAuthorizationEntity entity = ServiceInstanceAuthorizationEntity.builder()
                .processInstanceId(processInstanceId)
                .userId(userId)
                .build();
        this.processInstanceAuthorizationRepository.save(entity);
    }

    /**
     * Checks if a users has access to a process instance.
     *
     * @param instanceId Id of the instance
     * @param userId     Id of the user
     * @return
     */
    public boolean hasAccess(final String instanceId, final String userId) {
        return this.processInstanceAuthorizationRepository.findByUserIdAndProcessInstanceId(userId, instanceId).isPresent();
    }

}
