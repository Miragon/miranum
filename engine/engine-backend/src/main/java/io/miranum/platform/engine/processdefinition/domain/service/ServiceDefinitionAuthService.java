package io.miranum.platform.engine.processdefinition.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.authorization.Resources;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceDefinitionAuthService {

    private final AuthorizationService authorizationService;

    public boolean allowedToStartDefinition(final String userId, final List<String> groupIds, final String resourceId) {

        return this.authorizationService.isUserAuthorized(
                userId,
                groupIds,
                Permissions.CREATE_INSTANCE,
                Resources.PROCESS_DEFINITION,
                resourceId);
    }

}
