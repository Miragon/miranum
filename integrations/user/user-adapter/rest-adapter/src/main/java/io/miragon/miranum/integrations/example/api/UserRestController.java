package io.miragon.miranum.integrations.example.api;

import io.miragon.miranum.integrations.example.api.mapper.UserTOMapper;
import io.miragon.miranum.integrations.example.api.transport.SearchUserTO;
import io.miragon.miranum.integrations.example.api.transport.UserTO;
import io.miragon.miranum.integrations.user.application.port.in.SearchForUserParameter;
import io.miragon.miranum.integrations.user.application.port.in.SearchForUserQuery;
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

    private final SearchForUserQuery searchForUserQuery;
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
        val users = this.searchForUserQuery.searchUser(searchUserTO.getSearchString(), searchUserTO.getGroups());
        return ResponseEntity.ok(this.userMapper.map2TO(users));
    }

    /**
     * Get the logged in user.
     *
     * @return user
     */
    @GetMapping("/info")
    public ResponseEntity<UserTO> userinfo() {
        val user = this.searchForUserQuery.getUser(new SearchForUserParameter(this.userAuthenticationProvider.getCurrentUserId()));
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
        val user = this.searchForUserQuery.getUser(new SearchForUserParameter(id));
        return ResponseEntity.ok(this.userMapper.map2TO(user));
    }

    @GetMapping("/uid/{username}")
    public ResponseEntity<UserTO> getUserByUsername(@PathVariable("username") final String username) {
        val user = this.searchForUserQuery.getUserByUserName(username).get();
        return ResponseEntity.ok(this.userMapper.map2TO(user));
    }
}