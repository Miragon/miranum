package io.miragon.miranum.platform.example.shared.sso.init;

import org.camunda.bpm.engine.AuthorizationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationInitializer {

    public AuthenticationInitializer(
            @Value("${camunda.sso.webapps-role}") String webappsRole,
            @Value("${camunda.sso.worker-role}") String workerRole,
            AuthorizationService authorizationService
    ) {

        // User with admin rights
        AuthorizationHelper.setupGroupAppPermissions(authorizationService, webappsRole);
        AuthorizationHelper.setupGroupProcessDefinitionPermissions(authorizationService, webappsRole);
        AuthorizationHelper.setupGroupProcessInstancePermissions(authorizationService, webappsRole);
        AuthorizationHelper.setupUserTaskPermissions(authorizationService, webappsRole);
        AuthorizationHelper.setupGroupHistoricTaskPermissions(authorizationService, webappsRole);
        AuthorizationHelper.setupGroupHistoricProcessInstancePermissions(authorizationService, webappsRole);
        AuthorizationHelper.setupGroupBatchPermissions(authorizationService, webappsRole);
        AuthorizationHelper.setupGroupDashboardPermissions(authorizationService, webappsRole);
        AuthorizationHelper.setupGroupReportPermissions(authorizationService, webappsRole);
        AuthorizationHelper.setupGroupOpLogPermissions(authorizationService, webappsRole);
        AuthorizationHelper.setupGroupDeploymentPermissions(authorizationService, webappsRole);
        AuthorizationHelper.setupGroupDecisionRequirementPermissions(authorizationService, webappsRole);
        AuthorizationHelper.setupGroupDecisionPermissions(authorizationService, webappsRole);
        AuthorizationHelper.setupGroupSystemPermissions(authorizationService, webappsRole);
        AuthorizationHelper.setupGroupAuthorizationPermissions(authorizationService, webappsRole);
        AuthorizationHelper.setupGroupGroupPermissions(authorizationService, webappsRole);
        AuthorizationHelper.setupGroupGroupMembershipPermissions(authorizationService, webappsRole);
        AuthorizationHelper.setupGroupUserPermissions(authorizationService, webappsRole);

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
