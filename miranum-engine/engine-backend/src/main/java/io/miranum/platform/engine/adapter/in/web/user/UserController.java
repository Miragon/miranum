package io.miranum.platform.engine.adapter.in.web.user;

import io.miranum.platform.engine.application.port.in.user.UserQuery;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@RequestMapping("/rest/user")
@RequiredArgsConstructor
@Tag(name = "UserController", description = "API to get users")
public class UserController {

    private final UserQuery userQuery;

    //Mapper
    private final UserApiMapper userMapper;

    @PostMapping("/search")
    public ResponseEntity<List<UserDto>> getUsers(@RequestBody final SearchUserDto searchUserTO) {
        val users = this.userQuery.searchUser(searchUserTO.getSearchString());
        return ResponseEntity.ok(this.userMapper.map(users));
    }

    @GetMapping("/uid/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable("username") final String username) {
        val user = this.userQuery.getUserByUserName(username);
        return ResponseEntity.ok(this.userMapper.map(user));
    }

}

