package io.miragon.miranum.integrations.user.adapter.in.api;

import io.miragon.miranum.integrations.user.adapter.in.api.mapper.UserTOMapper;
import io.miragon.miranum.integrations.user.adapter.in.api.transport.SearchUserTO;
import io.miragon.miranum.integrations.user.adapter.in.api.transport.UserTO;
import io.miragon.miranum.integrations.user.application.port.in.SearchForUserParameter;
import io.miragon.miranum.integrations.user.application.service.UserService;
import io.miragon.miranum.integrations.user.common.AppAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API to get users.
 *
 * @author externer.dl.horn
 */
@RestController
@Transactional
@RequestMapping("/rest/user")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    private final AppAuthenticationProvider userAuthenticationProvider;
    private final UserTOMapper userMapper;

    /**
     * Search users for the given parameters.
     *
     * @param searchUserTO Search information
     * @return users
     */
    @PostMapping("/search")
    public ResponseEntity<List<UserTO>> getUsers(@RequestBody final SearchUserTO searchUserTO) {
        val users = this.userService.searchUser(searchUserTO.getSearchString(), searchUserTO.getGroups());
        return ResponseEntity.ok(this.userMapper.map2TO(users));
    }

    /**
     * Get the logged in user.
     *
     * @return user
     */
    @GetMapping("/info")
    public ResponseEntity<UserTO> userinfo() {
        val user = this.userService.getUser(new SearchForUserParameter(this.userAuthenticationProvider.getCurrentUserId()));
        return ResponseEntity.ok(this.userMapper.map2TO(user));
    }

    /**
     * Get user by id.
     *
     * @param id Id of the user
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserTO> getUser(@PathVariable("id") final String id) {
        val user = this.userService.getUser(new SearchForUserParameter(id));
        return ResponseEntity.ok(this.userMapper.map2TO(user));
    }

    @GetMapping("/uid/{username}")
    public ResponseEntity<UserTO> getUserByUsername(@PathVariable("username") final String username) {
        val user = this.userService.getUserByUserName(username).get();
        return ResponseEntity.ok(this.userMapper.map2TO(user));
    }
}