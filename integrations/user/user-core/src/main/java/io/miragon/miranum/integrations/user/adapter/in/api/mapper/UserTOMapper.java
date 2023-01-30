package io.miragon.miranum.integrations.user.adapter.in.api.mapper;

import io.miragon.miranum.integrations.user.adapter.in.api.transport.UserTO;
import io.miragon.miranum.integrations.user.common.BaseTOMapper;
import io.miragon.miranum.integrations.user.domain.User;
import org.mapstruct.Mapper;

/**
 * Map {@link User} domain object into {@link UserTO} transport object.
 *
 * @author externer.dl.horn
 */
@Mapper
public interface UserTOMapper extends BaseTOMapper<UserTO, User> {

}
