package io.miranum.platform.engine.adapter.in.web.user;

import io.miranum.platform.engine.domain.user.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserApiMapper {

    UserDto map(User user);

    List<UserDto> map(List<User> user);

}
