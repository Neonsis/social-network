package org.neonsis.socialnetwork.rest.controller;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.exception.RecordNotFoundException;
import org.neonsis.socialnetwork.model.dto.ProfileDto;
import org.neonsis.socialnetwork.model.dto.UserDto;
import org.neonsis.socialnetwork.rest.payload.mapper.AuthMapper;
import org.neonsis.socialnetwork.rest.payload.request.LoginRequest;
import org.neonsis.socialnetwork.rest.payload.request.SignUpRequest;
import org.neonsis.socialnetwork.rest.payload.response.UserAuthResponse;
import org.neonsis.socialnetwork.rest.security.JwtTokenProvider;
import org.neonsis.socialnetwork.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthMapper authMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            String email = loginRequest.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, loginRequest.getPassword()));
            UserDto loggedInUser = userService.findByEmail(email);
            String token = jwtTokenProvider.generateToken(email);
            loggedInUser.setToken(token);

            return new ResponseEntity<>(authMapper.userDtoToUserAuthResponse(loggedInUser), HttpStatus.OK);
        } catch (AuthenticationException | RecordNotFoundException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<UserAuthResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        UserDto userDto = authMapper.signUpRequestToUserDto(signUpRequest);
        ProfileDto profileDto = authMapper.signUpRequestToProfileDto(signUpRequest);

        UserDto registeredUser = userService.signUp(userDto, profileDto);
        String token = jwtTokenProvider.generateToken(registeredUser.getEmail());
        registeredUser.setToken(token);

        return new ResponseEntity<>(authMapper.userDtoToUserAuthResponse(registeredUser), HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
