package io.miranum.platform.tasklist.adapter.out.user;

import feign.FeignException;
import io.miranum.platform.tasklist.adapter.out.user.easyldap.EasyLdapClient;
import io.miranum.platform.tasklist.application.port.out.user.UserGroupResolverPort;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class LdapUserGroupResolverAdapter implements UserGroupResolverPort {

  private final EasyLdapClient easyLdapClient;

  @NonNull
  @Override
  public Set<String> resolveGroups(@NonNull final String userId) {
    try {
      return new HashSet<>(this.easyLdapClient.getOuTreeByUserId(userId).stream().map(String::toLowerCase).collect(Collectors.toList()));
    } catch (final FeignException e) {
      return Collections.emptySet();
    }
  }
}
