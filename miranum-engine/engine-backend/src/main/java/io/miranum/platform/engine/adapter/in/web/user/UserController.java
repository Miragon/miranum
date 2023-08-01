package io.miranum.platform.engine.adapter.in.web.user;

import io.miranum.platform.engine.api.AppAuthenticationProvider;
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
    private final AppAuthenticationProvider userAuthenticationProvider;

    //Mapper
    private final UserApiMapper userMapper;

    @PostMapping("/search")
    public ResponseEntity<List<UserDto>> getUsers(@RequestBody final SearchUserDto searchUserTO) {
        val users = this.userQuery.searchUser(searchUserTO.getSearchString());
        return ResponseEntity.ok(this.userMapper.map(users));
    }

    @GetMapping("/info")
    public ResponseEntity<UserDto> userinfo() {
        val user = this.userQuery.getUserById(this.userAuthenticationProvider.getCurrentUserId());
        return ResponseEntity.ok(this.userMapper.map(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") final String id) {
        val user = this.userQuery.getUserById(id);
        return ResponseEntity.ok(this.userMapper.map(user));
    }

    /**
     * Get user by username.
     *
     * @param username
     * @return user
     */
    @GetMapping("/uid/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable("username") final String username) {
        val user = this.userQuery.getUserByUserName(username);
        return ResponseEntity.ok(this.userMapper.map(user));
    }

}

