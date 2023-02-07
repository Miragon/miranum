package io.miragon.miranum.integrations.example.api;

import io.miragon.miranum.integrations.example.api.mapper.UserTOMapper;
import io.miragon.miranum.integrations.example.api.transport.UserTO;
import io.miragon.miranum.integrations.user.application.port.in.*;
import io.miragon.miranum.integrations.user.common.AppAuthenticationProvider;
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
public class UserRestController {

    private final SearchByIdQuery searchByIdQuery;

    private final SearchByNameQuery searchByNameQuery;

    private final AppAuthenticationProvider userAuthenticationProvider;

    private final UserTOMapper userMapper;

    @GetMapping("/info")
    public ResponseEntity<UserTO> getLoggedInUser() {
        val user = this.searchByIdQuery.searchById(new SearchByIdParameter(this.userAuthenticationProvider.getCurrentUserId()));
        return ResponseEntity.ok(this.userMapper.map2TO(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTO> getUserById(@PathVariable("id") final String id) {
        val user = this.searchByIdQuery.searchById(new SearchByIdParameter(id));
        return ResponseEntity.ok(this.userMapper.map2TO(user));
    }

    @GetMapping("/uid/{name}")
    public ResponseEntity<List<UserTO>> getUserByUsername(@PathVariable("name") final String name) {
        val users = this.searchByNameQuery.searchByName(new SearchByNameParameter(name));
        return ResponseEntity.ok(this.userMapper.map2TO(users.getUsers()));
    }
}