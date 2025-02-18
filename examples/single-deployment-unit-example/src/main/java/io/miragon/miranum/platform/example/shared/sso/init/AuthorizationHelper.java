package io.miragon.miranum.platform.example.shared.sso.init;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.authorization.Resources;

@Slf4j
public class AuthorizationHelper {

    public static void setupGroupAppPermissions(AuthorizationService authorizationService, String groupId) {
        if (existsByGroupIdAndResourceType(authorizationService, groupId, Resources.APPLICATION)) {
            return;
        }
        log.info("Setting up Web App Permissions for group '{}'", groupId);
        Authorization appAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        appAuth.setGroupId(groupId);
        appAuth.addPermission(Permissions.ACCESS);
        appAuth.setResource(Resources.APPLICATION);
        appAuth.setResourceId(Authorization.ANY);
        authorizationService.saveAuthorization(appAuth);
    }

    public static void setupUserTaskPermissions(AuthorizationService authorizationService, String groupId) {
        if (existsByGroupIdAndResourceType(authorizationService, groupId, Resources.TASK)) {
            return;
        }
        log.info("Setting up Camunda Task Permissions for group '{}'", groupId);
        Authorization taskAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        taskAuth.setGroupId(groupId);
        taskAuth.addPermission(Permissions.ALL);
        taskAuth.setResource(Resources.TASK);
        taskAuth.setResourceId(Authorization.ANY);
        authorizationService.saveAuthorization(taskAuth);
    }

    public static void setupGroupProcessDefinitionPermissions(AuthorizationService authorizationService, String groupId) {
        if (existsByGroupIdAndResourceType(authorizationService, groupId, Resources.PROCESS_DEFINITION)) {
            return;
        }
        log.info("Setting up Camunda Process Definition Permissions for group '{}'", groupId);
        Authorization processDefinitionAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        processDefinitionAuth.setGroupId(groupId);
        processDefinitionAuth.addPermission(Permissions.ALL);
        processDefinitionAuth.setResource(Resources.PROCESS_DEFINITION);
        processDefinitionAuth.setResourceId(Authorization.ANY);
        authorizationService.saveAuthorization(processDefinitionAuth);
    }

    public static void setupGroupHistoricProcessInstancePermissions(AuthorizationService authorizationService, String groupId) {
        if (existsByGroupIdAndResourceType(authorizationService, groupId, Resources.HISTORIC_PROCESS_INSTANCE)) {
            return;
        }
        log.info("Setting up Camunda Historic Process Instance Permissions for group '{}'", groupId);
        Authorization historicProcessInstanceAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        historicProcessInstanceAuth.setGroupId(groupId);
        historicProcessInstanceAuth.addPermission(Permissions.ALL);
        historicProcessInstanceAuth.setResource(Resources.HISTORIC_PROCESS_INSTANCE);
        historicProcessInstanceAuth.setResourceId(Authorization.ANY);
        authorizationService.saveAuthorization(historicProcessInstanceAuth);
    }

    public static void setupGroupDashboardPermissions(AuthorizationService authorizationService, String groupId) {
        if (existsByGroupIdAndResourceType(authorizationService, groupId, Resources.DASHBOARD)) {
            return;
        }
        log.info("Setting up Camunda Dashboard Permissions for groupId '{}'", groupId);
        Authorization dashboardAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        dashboardAuth.setGroupId(groupId);
        dashboardAuth.addPermission(Permissions.ALL);
        dashboardAuth.setResource(Resources.DASHBOARD);
        dashboardAuth.setResourceId(Authorization.ANY);
        authorizationService.saveAuthorization(dashboardAuth);
    }

    public static void setupGroupDecisionRequirementPermissions(AuthorizationService authorizationService, String groupId) {
        if (existsByGroupIdAndResourceType(authorizationService, groupId, Resources.DECISION_REQUIREMENTS_DEFINITION)) {
            return;
        }
        log.info("Setting up Camunda DRD Permissions for group '{}'", groupId);
        Authorization decisionRequirementsAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        decisionRequirementsAuth.setGroupId(groupId);
        decisionRequirementsAuth.addPermission(Permissions.ALL);
        decisionRequirementsAuth.setResource(Resources.DECISION_REQUIREMENTS_DEFINITION);
        decisionRequirementsAuth.setResourceId(Authorization.ANY);
        authorizationService.saveAuthorization(decisionRequirementsAuth);
    }

    public static void setupGroupDecisionPermissions(AuthorizationService authorizationService, String groupId) {
        if (existsByGroupIdAndResourceType(authorizationService, groupId, Resources.DECISION_DEFINITION)) {
            return;
        }
        log.info("Setting up Camunda Decision Permissions for group '{}'", groupId);
        Authorization decisionAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        decisionAuth.setGroupId(groupId);
        decisionAuth.addPermission(Permissions.ALL);
        decisionAuth.setResource(Resources.DECISION_DEFINITION);
        decisionAuth.setResourceId(Authorization.ANY);
        authorizationService.saveAuthorization(decisionAuth);
    }

    public static void setupGroupHistoricTaskPermissions(AuthorizationService authorizationService, String groupId) {
        if (existsByGroupIdAndResourceType(authorizationService, groupId, Resources.HISTORIC_TASK)) {
            return;
        }
        log.info("Setting up Camunda Historic Task Permissions for group '{}'", groupId);
        Authorization historicTaskAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        historicTaskAuth.setGroupId(groupId);
        historicTaskAuth.addPermission(Permissions.ALL);
        historicTaskAuth.setResource(Resources.HISTORIC_TASK);
        historicTaskAuth.setResourceId(Authorization.ANY);
        authorizationService.saveAuthorization(historicTaskAuth);
    }

    public static void setupGroupProcessInstancePermissions(AuthorizationService authorizationService, String groupId) {
        if (existsByGroupIdAndResourceType(authorizationService, groupId, Resources.PROCESS_INSTANCE)) {
            return;
        }
        log.info("Setting up Camunda Process Instance Permissions for user '{}'", groupId);
        Authorization processInstanceAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        processInstanceAuth.setGroupId(groupId);
        processInstanceAuth.addPermission(Permissions.ALL);
        processInstanceAuth.setResource(Resources.PROCESS_INSTANCE);
        processInstanceAuth.setResourceId(Authorization.ANY);
        authorizationService.saveAuthorization(processInstanceAuth);
    }

    public static void setupGroupDeploymentPermissions(AuthorizationService authorizationService, String groupId) {
        if (existsByGroupIdAndResourceType(authorizationService, groupId, Resources.DEPLOYMENT)) {
            return;
        }
        log.info("Setting up Camunda Deployment Permissions for user '{}'", groupId);
        Authorization deploymentAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        deploymentAuth.setGroupId(groupId);
        deploymentAuth.addPermission(Permissions.ALL);
        deploymentAuth.setResource(Resources.DEPLOYMENT);
        deploymentAuth.setResourceId(Authorization.ANY);
        authorizationService.saveAuthorization(deploymentAuth);
    }

    public static void setupGroupBatchPermissions(AuthorizationService authorizationService, String groupId) {
        if (existsByGroupIdAndResourceType(authorizationService, groupId, Resources.BATCH)) {
            return;
        }
        log.info("Setting up Batch Permissions for user '{}'", groupId);
        Authorization batchAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        batchAuth.setGroupId(groupId);
        batchAuth.addPermission(Permissions.CREATE);
        batchAuth.addPermission(Permissions.READ);
        batchAuth.addPermission(Permissions.DELETE);
        batchAuth.addPermission(Permissions.UPDATE);
        batchAuth.setResource(Resources.BATCH);
        batchAuth.setResourceId(Authorization.ANY);
        authorizationService.saveAuthorization(batchAuth);
    }

    public static void setupGroupReportPermissions(AuthorizationService authorizationService, String groupId) {
        if (existsByGroupIdAndResourceType(authorizationService, groupId, Resources.REPORT)) {
            return;
        }
        log.info("Setting up Camunda Report Permissions for user '{}'", groupId);
        Authorization reportAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        reportAuth.setGroupId(groupId);
        reportAuth.addPermission(Permissions.ALL);
        reportAuth.setResource(Resources.REPORT);
        reportAuth.setResourceId(Authorization.ANY);
        authorizationService.saveAuthorization(reportAuth);
    }

    public static void setupGroupOpLogPermissions(AuthorizationService authorizationService, String groupId) {
        if (existsByGroupIdAndResourceType(authorizationService, groupId, Resources.REPORT)) {
            return;
        }
        if (authorizationService.createAuthorizationQuery().groupIdIn(groupId).resourceType(Resources.OPERATION_LOG_CATEGORY).count() != 0L) {
            return;
        }
        log.info("Setting up Camunda Operation Log Permissions for user '{}'", groupId);
        Authorization opLogAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        opLogAuth.setGroupId(groupId);
        opLogAuth.addPermission(Permissions.ALL);
        opLogAuth.setResource(Resources.OPERATION_LOG_CATEGORY);
        opLogAuth.setResourceId(Authorization.ANY);
        authorizationService.saveAuthorization(opLogAuth);
    }

    public static void setupGroupSystemPermissions(AuthorizationService authorizationService, String groupId) {
        if (existsByGroupIdAndResourceType(authorizationService, groupId, Resources.SYSTEM)) {
            return;
        }
        log.info("Setting up Camunda System Permissions for user '{}'", groupId);
        Authorization systemAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        systemAuth.setGroupId(groupId);
        systemAuth.addPermission(Permissions.ALL);
        systemAuth.setResource(Resources.SYSTEM);
        systemAuth.setResourceId(Authorization.ANY);
        authorizationService.saveAuthorization(systemAuth);
    }

    public static void setupGroupAuthorizationPermissions(AuthorizationService authorizationService, String groupId) {
        if (existsByGroupIdAndResourceType(authorizationService, groupId, Resources.AUTHORIZATION)) {
            return;
        }
        log.info("Setting up Authorization Permissions for group '{}'", groupId);
        Authorization authorizationAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        authorizationAuth.setGroupId(groupId);
        authorizationAuth.addPermission(Permissions.ALL);
        authorizationAuth.setResource(Resources.AUTHORIZATION);
        authorizationAuth.setResourceId(Authorization.ANY);
        authorizationService.saveAuthorization(authorizationAuth);
    }

    public static void setupGroupGroupPermissions(AuthorizationService authorizationService, String groupId) {
        if (authorizationService.createAuthorizationQuery().groupIdIn(groupId).resourceType(Resources.GROUP).count() != 0L) {
            return;
        }
        log.info("Setting up Group Permissions for group '{}'", groupId);
        Authorization groupAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        groupAuth.setGroupId(groupId);
        groupAuth.addPermission(Permissions.ALL);
        groupAuth.setResource(Resources.GROUP);
        groupAuth.setResourceId(Authorization.ANY);
        authorizationService.saveAuthorization(groupAuth);
    }

    public static void setupGroupGroupMembershipPermissions(AuthorizationService authorizationService, String groupId) {
        if (authorizationService.createAuthorizationQuery().groupIdIn(groupId).resourceType(Resources.GROUP_MEMBERSHIP).count() != 0L) {
            return;
        }
        log.info("Setting up Group Membership Permissions for group '{}'", groupId);
        Authorization groupMembershipAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        groupMembershipAuth.setGroupId(groupId);
        groupMembershipAuth.addPermission(Permissions.ALL);
        groupMembershipAuth.setResource(Resources.GROUP_MEMBERSHIP);
        groupMembershipAuth.setResourceId(Authorization.ANY);
        authorizationService.saveAuthorization(groupMembershipAuth);
    }

    public static void setupGroupUserPermissions(AuthorizationService authorizationService, String groupId) {
        if (authorizationService.createAuthorizationQuery().groupIdIn(groupId).resourceType(Resources.USER).count() != 0L) {
            return;
        }
        log.info("Setting up User Permissions for group '{}'", groupId);
        Authorization userAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        userAuth.setGroupId(groupId);
        userAuth.addPermission(Permissions.ALL);
        userAuth.setResource(Resources.USER);
        userAuth.setResourceId(Authorization.ANY);
        authorizationService.saveAuthorization(userAuth);
    }

    private static boolean existsByGroupIdAndResourceType(AuthorizationService authorizationService, String groupId, Resources resources) {
        return authorizationService.createAuthorizationQuery().groupIdIn(groupId).resourceType(resources).count() != 0L;
    }
}

