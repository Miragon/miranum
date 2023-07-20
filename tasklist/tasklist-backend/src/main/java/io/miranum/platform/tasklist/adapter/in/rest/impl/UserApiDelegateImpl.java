package io.miranum.platform.tasklist.adapter.in.rest.impl;

import io.miranum.platform.tasklist.adapter.in.rest.api.UserApiDelegate;
import io.miranum.platform.tasklist.adapter.in.rest.mapper.UserProfileMapper;
import io.miranum.platform.tasklist.adapter.in.rest.model.UserProfileTO;
import io.miranum.platform.tasklist.application.port.in.ResolveUserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static org.springframework.http.ResponseEntity.ok;

@Component
@RequiredArgsConstructor
public class UserApiDelegateImpl implements UserApiDelegate {

  private final UserProfileMapper userMapper;
  private final ResolveUserProfile resolvePort;
  @Override
  public ResponseEntity<UserProfileTO> resolveUser(String userId) {
    // use the safe resolution which will fall back to the Unknown user, if the profile can't be resolved
    return ok(userMapper.to(resolvePort.findUserProfile(userId)));
  }
}
