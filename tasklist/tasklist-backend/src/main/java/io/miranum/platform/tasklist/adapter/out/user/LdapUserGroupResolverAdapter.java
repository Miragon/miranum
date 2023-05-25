package io.miranum.platform.tasklist.adapter.out.user;

import feign.FeignException;
import io.miranum.platform.tasklist.adapter.out.user.easyldap.EasyLdapClient;
import io.miranum.platform.tasklist.adapter.out.user.easyldap.UserInfoResponse;
import io.miranum.platform.tasklist.application.port.out.user.UserGroupResolverPort;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
public class LdapUserGroupResolverAdapter implements UserGroupResolverPort {

    private final EasyLdapClient easyLdapClient;

    @NonNull
    @Override
    public Set<String> resolveGroups(@NonNull String userId) {
        try {
            UserInfoResponse userInfoResponse = easyLdapClient.getUserById(userId);
            return Collections.singleton(userInfoResponse.getOrganizationalUnit());
        } catch (FeignException e) {
            return Collections.emptySet();
        }
    }
}
