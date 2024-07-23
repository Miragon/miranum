package io.miragon.miranum.platform.example.engine.sso.init;

import org.camunda.bpm.engine.AuthorizationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationInitializer {

    @Value("${camunda.sso.webapp-role}")
    private String webAppRole;

    @Value("${camunda.sso.worker-role}")
    private String workerRole;

    public AuthenticationInitializer(AuthorizationService authorizationService) {

        // User with admin rights
        AuthorizationHelper.setupGroupAppPermissions(authorizationService, webAppRole);
        AuthorizationHelper.setupGroupProcessDefinitionPermissions(authorizationService, webAppRole);
        AuthorizationHelper.setupGroupProcessInstancePermissions(authorizationService, webAppRole);
        AuthorizationHelper.setupUserTaskPermissions(authorizationService, webAppRole);
        AuthorizationHelper.setupGroupHistoricTaskPermissions(authorizationService, webAppRole);
        AuthorizationHelper.setupGroupHistoricProcessInstancePermissions(authorizationService, webAppRole);
        AuthorizationHelper.setupGroupBatchPermissions(authorizationService, webAppRole);
        AuthorizationHelper.setupGroupDashboardPermissions(authorizationService, webAppRole);
        AuthorizationHelper.setupGroupReportPermissions(authorizationService, webAppRole);
        AuthorizationHelper.setupGroupOpLogPermissions(authorizationService, webAppRole);
        AuthorizationHelper.setupGroupDeploymentPermissions(authorizationService, webAppRole);
        AuthorizationHelper.setupGroupDecisionRequirementPermissions(authorizationService, webAppRole);
        AuthorizationHelper.setupGroupDecisionPermissions(authorizationService, webAppRole);
        AuthorizationHelper.setupGroupSystemPermissions(authorizationService, webAppRole);
        AuthorizationHelper.setupGroupAuthorizationPermissions(authorizationService, webAppRole);
        AuthorizationHelper.setupGroupGroupPermissions(authorizationService, webAppRole);
        AuthorizationHelper.setupGroupGroupMembershipPermissions(authorizationService, webAppRole);
        AuthorizationHelper.setupGroupUserPermissions(authorizationService, webAppRole);

        // Worker
        AuthorizationHelper.setupGroupAppPermissions(authorizationService, workerRole);
        AuthorizationHelper.setupGroupProcessDefinitionPermissions(authorizationService, workerRole);
        AuthorizationHelper.setupGroupProcessInstancePermissions(authorizationService, workerRole);
        AuthorizationHelper.setupUserTaskPermissions(authorizationService, workerRole);
        AuthorizationHelper.setupGroupHistoricTaskPermissions(authorizationService, workerRole);
        AuthorizationHelper.setupGroupHistoricProcessInstancePermissions(authorizationService, workerRole);
        AuthorizationHelper.setupGroupDashboardPermissions(authorizationService, workerRole);
        AuthorizationHelper.setupGroupDecisionRequirementPermissions(authorizationService, workerRole);
        AuthorizationHelper.setupGroupDecisionPermissions(authorizationService, workerRole);
        AuthorizationHelper.setupGroupDeploymentPermissions(authorizationService, workerRole);

    }
    
}
