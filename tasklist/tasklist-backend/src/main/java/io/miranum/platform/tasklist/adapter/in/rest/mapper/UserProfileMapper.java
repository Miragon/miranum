package io.miranum.platform.tasklist.adapter.in.rest.mapper;

import io.miranum.platform.tasklist.adapter.in.rest.model.UserProfileTO;
import io.miranum.platform.tasklist.domain.UserProfile;
import lombok.val;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface UserProfileMapper {


  default UserProfileTO to(UserProfile profile) {
    val to = new UserProfileTO();
    to.setFirstName(profile.getFirstName());
    to.setLastName(profile.getLastName());
    to.setUserId(profile.getUserId());
    to.setPrimaryOrgUnit(profile.getPrimaryOrgUnit());
    return to;
  }
}
