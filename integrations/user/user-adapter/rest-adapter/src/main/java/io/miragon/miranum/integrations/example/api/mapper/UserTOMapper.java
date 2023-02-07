package io.miragon.miranum.integrations.example.api.mapper;

import io.miragon.miranum.integrations.example.api.transport.UserTO;
import io.miragon.miranum.integrations.user.domain.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserTOMapper extends BaseTOMapper<UserTO, User> {

}
