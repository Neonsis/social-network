package org.neonsis.socialnetwork.rest.controller;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.rest.payload.mapper.AuthMapper;
import org.neonsis.socialnetwork.rest.payload.response.UserResponse;
import org.neonsis.socialnetwork.rest.security.CurrentUser;
import org.neonsis.socialnetwork.rest.security.UserPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthMapper authMapper;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public UserResponse getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return authMapper.userPrincipalToUserResponse(userPrincipal);
    }
}
